package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Tv
import id.riverflows.core.domain.model.TvDetail
import id.riverflows.core.domain.repository.ITvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvInteractor @Inject constructor(private val repository: ITvRepository): TvUseCase {
    override fun getAllTvShows(): Flow<Resource<List<Tv>>> = repository.getTvShows()

    override fun getDetailTv(id: Long): Flow<Resource<TvDetail>> = repository.getDetailTv(id)
}