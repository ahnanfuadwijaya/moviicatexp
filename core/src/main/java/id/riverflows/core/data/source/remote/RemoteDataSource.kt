package id.riverflows.core.data.source.remote

import android.util.Log
import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.network.ApiService
import id.riverflows.core.data.source.remote.response.MovieDetailResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvDetailResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: Long): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try{
                val response = apiService.getMovieDetail(id)
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTvShows(): Flow<ApiResponse<List<TvResponse>>> {
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
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTv(id: Long): Flow<ApiResponse<TvDetailResponse>> {
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

