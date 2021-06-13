package id.riverflows.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.riverflows.core.data.source.local.entity.Favorite
import id.riverflows.core.data.source.local.entity.MovieDetailEntity
import id.riverflows.core.data.source.local.entity.TvDetailEntity

@Suppress("SpellCheckingInspection")
@Database(entities = [MovieDetailEntity::class, TvDetailEntity::class, Favorite.FavoriteEntity::class], version = 1, exportSchema = false)
abstract class MoviiCatDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
    abstract fun favoriteDao(): FavoriteDao
}