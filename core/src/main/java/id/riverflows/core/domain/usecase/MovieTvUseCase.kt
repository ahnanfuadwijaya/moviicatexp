package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Content.MovieTv
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getMovies(): Flow<Resource<List<MovieTv>>>
    fun getDetailMovie(id: Long): Flow<Resource<MovieTv>>
    fun getTvShows(): Flow<Resource<List<MovieTv>>>
    fun getDetailTv(id: Long): Flow<Resource<MovieTv>>
    fun getFavoriteMovies(): Flow<List<MovieTv>>
    fun getFavoriteMovie(id: Long): Flow<MovieTv>
    fun getFavoriteTvShows(): Flow<List<MovieTv>>
    fun getFavoriteTvShow(id: Long): Flow<MovieTv>
    fun setFavorite(data: MovieTv)
}