package id.riverflows.core.data.source.local

import id.riverflows.core.data.source.local.entity.Favorite.FavoriteEntity
import id.riverflows.core.data.source.local.entity.MovieDetailEntity
import id.riverflows.core.data.source.local.entity.TvDetailEntity
import id.riverflows.core.data.source.local.room.FavoriteDao
import id.riverflows.core.data.source.local.room.MovieDao
import id.riverflows.core.data.source.local.room.TvDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val tvDao: TvDao,
    private val favoriteDao: FavoriteDao
) {

    fun getMovies(): Flow<List<MovieDetailEntity>> = movieDao.getMovies()

    fun getDetailMovie(id: Long): Flow<MovieDetailEntity> = movieDao.getDetailMovie(id)

    fun getFavoriteMovies(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteMovies()

    fun getFavoriteMovie(id: Long): Flow<FavoriteEntity> = favoriteDao.getFavoriteMovie(id)

    suspend fun insertMovies(list: List<MovieDetailEntity>) = movieDao.insertMovies(list)

    suspend fun insertMovie(data: MovieDetailEntity) = movieDao.insertMovie(data)

    fun getTvShows(): Flow<List<TvDetailEntity>> = tvDao.getTvShows()

    fun getFavoriteTvShows(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteTvShows()

    suspend fun insertTvShows(list: List<TvDetailEntity>) = tvDao.insertTvShows(list)

    fun setFavorite(data: FavoriteEntity) = favoriteDao.updateFavorite(data)
}