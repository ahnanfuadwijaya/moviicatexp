package id.riverflows.core.domain.repository

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Favorite
import id.riverflows.core.domain.model.Movie
import id.riverflows.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Long): Flow<Resource<MovieDetail>>
}