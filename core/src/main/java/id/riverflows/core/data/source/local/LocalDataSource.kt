package id.riverflows.core.data.source.local

import id.riverflows.core.data.source.local.entity.MovieTvEntity
import id.riverflows.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(
    private val movieTvDao: MovieTvDao
) {

    fun getMovies(): Flow<List<MovieTvEntity>> = movieTvDao.getMovies()

    fun getDetailMovie(id: Long): Flow<MovieTvEntity?> = movieTvDao.getDetailMovie(id)

    fun getTvShows(): Flow<List<MovieTvEntity>> = movieTvDao.getTvShows()

    fun getDetailTv(id: Long): Flow<MovieTvEntity?> = movieTvDao.getDetailTv(id)

    suspend fun insertList(list: List<MovieTvEntity>) = movieTvDao.insertList(list)

    suspend fun insertData(data: MovieTvEntity) = movieTvDao.insertData(data)

    fun getFavoriteMovies(): Flow<List<MovieTvEntity>> = movieTvDao.getFavoriteMovies()

    fun getFavoriteTvShows(): Flow<List<MovieTvEntity>> = movieTvDao.getFavoriteTvShows()

    fun getFavoriteMoviesSearchResult(query: String) = movieTvDao.getFavoriteMoviesSearchResult(query)

    fun getFavoriteTvShowsSearchResult(query: String) = movieTvDao.getFavoriteTvShowsSearchResult(query)
}