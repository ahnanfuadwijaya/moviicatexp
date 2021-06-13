package id.riverflows.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.riverflows.core.data.source.local.room.MovieTvDao
import id.riverflows.core.data.source.local.room.MoviiCatDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Suppress("SpellCheckingInspection")
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviiCatDatabase = Room.databaseBuilder(
        context, MoviiCatDatabase::class.java, "MoviiCat.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: MoviiCatDatabase): MovieTvDao = database.movieTvDao()
}