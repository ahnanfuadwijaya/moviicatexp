package id.riverflows.moviicatexp.home

import androidx.lifecycle.*
import id.riverflows.core.data.Resource
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
class HomeSharedViewModel(
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    private val _movies = MutableLiveData<Resource<List<MovieTv>>>()
    val movies: LiveData<Resource<List<MovieTv>>> = _movies
    private val _tvShows = MutableLiveData<Resource<List<MovieTv>>>()
    val tvShows: LiveData<Resource<List<MovieTv>>> = _tvShows
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val searchMoviesResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieTvUseCase.searchMovies(it, 1L)
        }
        .asLiveData()

    val searchTvShowsResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieTvUseCase.searchTvShows(it, 1L)
        }
        .asLiveData()

    fun getMovies() = viewModelScope.launch {
        movieTvUseCase.getMovies().collect {
            _movies.postValue(it)
        }
    }

    fun getTvShows() = viewModelScope.launch {
        movieTvUseCase.getTvShows().collect {
            _tvShows.postValue(it)
        }
    }
}