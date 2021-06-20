package id.riverflows.moviicatexp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel (
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    private val _data = MutableLiveData<Resource<MovieTv>>()
    val data: LiveData<Resource<MovieTv>> = _data

    fun getDetailMovie(id: Long) = viewModelScope.launch {
        movieTvUseCase.getDetailMovie(id).collect {
            _data.postValue(it)
        }
    }

    fun getDetailTv(id: Long) = viewModelScope.launch {
        movieTvUseCase.getDetailTv(id).collect {
            _data.postValue(it)
        }
    }

    fun updateData(data: MovieTv) = viewModelScope.launch(Dispatchers.IO){
        movieTvUseCase.updateData(data)
    }
}