package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Movie
import id.riverflows.core.domain.model.MovieDetail
import id.riverflows.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val repository: IMovieRepository): MovieUseCase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> = repository.getMovies()

    override fun getDetailMovie(id: Long): Flow<Resource<MovieDetail>> = repository.getDetailMovie(id)
}