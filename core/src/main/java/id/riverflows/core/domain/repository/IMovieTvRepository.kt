package id.riverflows.core.domain.repository

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import kotlinx.coroutines.flow.Flow

interface IMovieTvRepository {
    fun getMovies(): Flow<Resource<List<MovieTv>>>
    fun getDetailMovie(id: Long): Flow<Resource<MovieTv>>
    fun getTvShows(): Flow<Resource<List<MovieTv>>>
    fun getDetailTv(id: Long): Flow<Resource<MovieTv>>
    suspend fun updateData(data: MovieTv)
    fun getFavoriteMovies(): Flow<List<MovieTv>>
    fun getFavoriteTvShows(): Flow<List<MovieTv>>
    fun searchMovies(query: String, page: Long): Flow<Resource<List<MovieTv>>>
    fun searchTvShows(query: String, page: Long): Flow<Resource<List<MovieTv>>>
    fun searchFavoriteMovies(query: String): Flow<List<MovieTv>>
    fun searchFavoriteTvShows(query: String): Flow<List<MovieTv>>
}