package id.riverflows.core.data.source.local

import id.riverflows.core.data.source.local.entity.Entity.MovieTv
import id.riverflows.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(
    private val movieTvDao: MovieTvDao
) {

    fun getMovies(): Flow<List<MovieTv>> = movieTvDao.getMovies()

    fun getDetailMovie(id: Long): Flow<MovieTv> = movieTvDao.getDetailMovie(id)

    fun getTvShows(): Flow<List<MovieTv>> = movieTvDao.getTvShows()

    fun getDetailTv(id: Long): Flow<MovieTv> = movieTvDao.getDetailTv(id)

    suspend fun insertList(list: List<MovieTv>) = movieTvDao.insertList(list)

    suspend fun updateData(data: MovieTv) = movieTvDao.updateData(data)

    fun getFavoriteMovies(): Flow<List<MovieTv>> = movieTvDao.getFavoriteMovies()

    fun getFavoriteTvShows(): Flow<List<MovieTv>> = movieTvDao.getFavoriteTvShows()
}