package id.riverflows.core.domain.repository

import id.riverflows.core.domain.model.Favorite
import id.riverflows.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getFavoriteMovies(): Flow<List<Favorite>>
    fun getFavoriteMovie(id: Long): Flow<Favorite>
    fun getFavoriteTvShows(): Flow<List<Favorite>>
    fun getFavoriteTvShow(id: Long): Flow<Favorite>
    fun setFavorite(data: Favorite)
}