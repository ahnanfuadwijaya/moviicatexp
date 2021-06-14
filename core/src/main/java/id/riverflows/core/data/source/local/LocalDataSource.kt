package id.riverflows.core.data.source.local

import id.riverflows.core.data.source.local.entity.Entity.MovieTv
import id.riverflows.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieTvDao: MovieTvDao
) {

    fun getMovies(): Flow<List<MovieTv>> = movieTvDao.getMovies()

    fun getDetailMovie(id: Long): Flow<MovieTv> = movieTvDao.getDetailMovie(id)

    fun getTvShows(): Flow<List<MovieTv>> = movieTvDao.getTvShows()

    fun getDetailTv(id: Long): Flow<MovieTv> = movieTvDao.getDetailTv(id)

    suspend fun insertList(list: List<MovieTv>) = movieTvDao.insertList(list)

    suspend fun insertData(data: MovieTv) = movieTvDao.insertData(data)

    suspend fun updateData(data: MovieTv) = movieTvDao.updateData(data)

    fun getFavoriteMovies(): Flow<List<MovieTv>> = movieTvDao.getFavoriteMovies()

    fun getFavoriteMovie(id: Long): Flow<MovieTv?> = movieTvDao.getFavoriteMovie(id)

    fun getFavoriteTvShows(): Flow<List<MovieTv>> = movieTvDao.getFavoriteTvShows()

    fun getFavoriteTv(id: Long): Flow<MovieTv?> = movieTvDao.getFavoriteTv(id)
}