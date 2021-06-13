package id.riverflows.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.riverflows.core.data.source.local.entity.Entity

@Suppress("SpellCheckingInspection")
@Database(entities = [Entity.MovieTv::class], version = 1, exportSchema = false)
abstract class MoviiCatDatabase: RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao
}