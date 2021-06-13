package id.riverflows.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import id.riverflows.core.data.source.local.entity.Favorite
import id.riverflows.core.data.source.local.entity.Favorite.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE type = ${Favorite.TYPE_MOVIE}")
    fun getFavoriteMovies(): Flow<List<FavoriteEntity>>
    @Query("SELECT * FROM favorites WHERE type = ${Favorite.TYPE_TV}")
    fun getFavoriteTvShows(): Flow<List<FavoriteEntity>>
    @Query("SELECT * FROM favorites WHERE reference_id = :id AND type = ${Favorite.TYPE_MOVIE}")
    fun getFavoriteMovie(id: Long): Flow<FavoriteEntity>
    @Query("SELECT * FROM favorites WHERE reference_id = :id AND type = ${Favorite.TYPE_TV}")
    fun getFavoriteTv(id: Long): Flow<FavoriteEntity>
    @Update
    fun updateFavorite(data: FavoriteEntity)
}