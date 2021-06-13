package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Movie
import id.riverflows.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getDetailMovie(id: Long): Flow<Resource<MovieDetail>>
}