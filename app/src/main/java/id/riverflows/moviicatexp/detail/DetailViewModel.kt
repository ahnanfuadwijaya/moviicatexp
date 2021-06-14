package id.riverflows.moviicatexp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.riverflows.core.domain.model.Content
import id.riverflows.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    fun getDetailMovie(id: Long) = movieTvUseCase.getDetailMovie(id).asLiveData()

    fun getDetailTv(id: Long) = movieTvUseCase.getDetailTv(id).asLiveData()

    fun updateData(data: Content.MovieTv) = viewModelScope.launch(Dispatchers.IO){
        movieTvUseCase.updateData(data)
    }

    fun getFavoriteMovie(id: Long) = movieTvUseCase.getFavoriteMovie(id).asLiveData()

    fun getFavoriteTv(id: Long) = movieTvUseCase.getFavoriteTvShow(id).asLiveData()

    fun setFavorite(data: Content.MovieTv) = viewModelScope.launch(Dispatchers.IO){
        movieTvUseCase.setFavorite(data)
    }

    fun removeFavorite(data: Content.MovieTv) = viewModelScope.launch(Dispatchers.IO){
        movieTvUseCase.removeFavorite(data)
    }
}