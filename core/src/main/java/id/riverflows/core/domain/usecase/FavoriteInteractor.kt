package id.riverflows.core.domain.usecase

import id.riverflows.core.domain.model.Favorite
import id.riverflows.core.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(private val repository: IFavoriteRepository): FavoriteUseCase {
    override fun getFavoriteMovies(): Flow<List<Favorite>> = repository.getFavoriteMovies()

    override fun getFavoriteMovie(id: Long): Flow<Favorite> = repository.getFavoriteMovie(id)

    override fun getFavoriteTvShows(): Flow<List<Favorite>> = repository.getFavoriteTvShows()

    override fun getFavoriteTvShow(id: Long): Flow<Favorite> = repository.getFavoriteTvShow(id)

    override fun setFavorite(data: Favorite) = repository.setFavorite(data)
}