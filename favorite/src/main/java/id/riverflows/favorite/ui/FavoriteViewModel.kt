package id.riverflows.favorite.ui

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
class FavoriteViewModel(
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    private val _movies = MutableLiveData<List<MovieTv>>()
    val movies: LiveData<List<MovieTv>> = _movies
    private val _tvShows = MutableLiveData<List<MovieTv>>()
    val tvShows: LiveData<List<MovieTv>> = _tvShows
    val moviesQueryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    val tvShowsQueryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    fun getFavoriteMovies() = viewModelScope.launch {
        movieTvUseCase.getFavoriteMovies().collect {
            _movies.postValue(it)
        }
    }

    fun getFavoriteTvShows() = viewModelScope.launch {
        movieTvUseCase.getFavoriteTvShows().collect {
            _tvShows.postValue(it)
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

    val tvShowsResult = tvShowsQueryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieTvUseCase.searchFavoriteTvShows(it)
        }
        .asLiveData()
}