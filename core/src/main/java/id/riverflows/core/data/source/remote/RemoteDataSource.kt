package id.riverflows.core.data.source.remote

import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.network.ApiService
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse.Item>>> {
        return flow {
            try {
                val response = apiService.getMovies()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.d(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: Long): Flow<ApiResponse<MovieResponse.Detail>> {
        return flow {
            try{
                val response = apiService.getMovieDetail(id)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvResponse.Item>>> {
        return flow {
            try {
                val response = apiService.getTvShows()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.d(e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTv(id: Long): Flow<ApiResponse<TvResponse.Detail>> {
        return flow {
            try{
                val response = apiService.getTvDetail(id)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}

