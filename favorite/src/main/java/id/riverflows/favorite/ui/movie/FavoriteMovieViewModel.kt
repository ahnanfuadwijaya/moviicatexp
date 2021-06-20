package id.riverflows.favorite.ui.movie

import androidx.lifecycle.*
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteMovieViewModel(
    private val movieTvUseCase: MovieTvUseCase
):ViewModel() {
    val moviesQueryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    private val _movies = MutableLiveData<List<MovieTv>>()
    val movies: LiveData<List<MovieTv>> = _movies

    fun getFavoriteMovies() = viewModelScope.launch {
        movieTvUseCase.getFavoriteMovies().collect {
            _movies.postValue(it)
        }
    }

    val moviesResult = moviesQueryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieTvUseCase.searchFavoriteMovies(it)
        }
        .asLiveData()
}