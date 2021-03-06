package id.riverflows.core.domain.usecase

import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.domain.repository.IMovieTvRepository
import kotlinx.coroutines.flow.Flow

class MovieTvInteractor(
    private val repository: IMovieTvRepository
): MovieTvUseCase {

    override fun getMovies(): Flow<Resource<List<MovieTv>>> = repository.getMovies()

    override fun getDetailMovie(id: Long): Flow<Resource<MovieTv>> = repository.getDetailMovie(id)

    override fun getTvShows(): Flow<Resource<List<MovieTv>>> = repository.getTvShows()

    override fun getDetailTv(id: Long): Flow<Resource<MovieTv>> = repository.getDetailTv(id)

    override suspend fun updateData(data: MovieTv) = repository.updateData(data)

    override fun getFavoriteMovies(): Flow<List<MovieTv>> = repository.getFavoriteMovies()

    override fun getFavoriteTvShows(): Flow<List<MovieTv>> = repository.getFavoriteTvShows()

    override suspend fun searchMovies(query: String, page: Long): Flow<Resource<List<MovieTv>>> = repository.searchMovies(query, page)

    override suspend fun searchTvShows(query: String, page: Long): Flow<Resource<List<MovieTv>>> = repository.searchTvShows(query, page)

    override fun searchFavoriteMovies(query: String): Flow<List<MovieTv>> = repository.searchFavoriteMovies(query)

    override fun searchFavoriteTvShows(query: String): Flow<List<MovieTv>> = repository.searchFavoriteTvShows(query)
}