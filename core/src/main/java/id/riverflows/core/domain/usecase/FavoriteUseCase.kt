package id.riverflows.core.domain.usecase

import id.riverflows.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<Favorite>>
    fun getFavoriteMovie(id: Long): Flow<Favorite>
    fun getFavoriteTvShows(): Flow<List<Favorite>>
    fun getFavoriteTvShow(id: Long): Flow<Favorite>
    fun setFavorite(data: Favorite)
}