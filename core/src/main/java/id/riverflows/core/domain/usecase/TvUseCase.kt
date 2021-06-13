package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Tv
import id.riverflows.core.domain.model.TvDetail
import kotlinx.coroutines.flow.Flow

interface TvUseCase {
    fun getAllTvShows(): Flow<Resource<List<Tv>>>
    fun getDetailTv(id: Long): Flow<Resource<TvDetail>>
}