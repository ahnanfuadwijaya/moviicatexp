package id.riverflows.core.data.source.local.room

import androidx.room.*
import id.riverflows.core.data.source.local.entity.MovieTvEntity
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.core.utils.UtilConstants.TYPE_TV
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {
    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE")
    fun getMovies(): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND id = :id")
    fun getDetailMovie(id: Long): Flow<MovieTvEntity>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV")
    fun getTvShows(): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND id = :id")
    fun getDetailTv(id: Long): Flow<MovieTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<MovieTvEntity>)

    @Update
    suspend fun updateData(data: MovieTvEntity)


    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND is_favorite = 1")
    fun getFavoriteTvShows(): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE id = :id AND type = $TYPE_MOVIE AND is_favorite = 1")
    fun getFavoriteMovie(id: Long): Flow<MovieTvEntity?>

    @Query("SELECT * FROM movies_tv_shows WHERE id = :id AND type = $TYPE_TV AND is_favorite = 1")
    fun getFavoriteTv(id: Long): Flow<MovieTvEntity?>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND title LIKE '%'||:query||'%'")
    fun getMoviesSearchResult(query: String): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND title LIKE '%'||:query||'%'")
    fun getTvShowsSearchResult(query: String): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND is_favorite = 1 AND title LIKE '%'||:query||'%'")
    fun getFavoriteMoviesSearchResult(query: String): Flow<List<MovieTvEntity>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND is_favorite = 1 AND title LIKE '%'||:query||'%'")
    fun getFavoriteTvShowsSearchResult(query: String): Flow<List<MovieTvEntity>>
}