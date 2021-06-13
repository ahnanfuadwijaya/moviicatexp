package id.riverflows.moviicatexp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.riverflows.core.domain.usecase.MovieTvUseCase
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: MovieTvUseCase
): ViewModel() {
    fun getDetailMovie(id: Long) = useCase.getDetailMovie(id).asLiveData()
    fun getDetailTv(id: Long) = useCase.getDetailTv(id).asLiveData()
}