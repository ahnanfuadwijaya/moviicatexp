package id.riverflows.favorite.ui.tv

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
class FavoriteTvViewModel(
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    val tvShowsQueryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    private val _tvShows = MutableLiveData<List<MovieTv>>()
    val tvShows: LiveData<List<MovieTv>> = _tvShows

    fun getFavoriteTvShows() = viewModelScope.launch {
        movieTvUseCase.getFavoriteTvShows().collect {
            _tvShows.postValue(it)
        }
    }

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