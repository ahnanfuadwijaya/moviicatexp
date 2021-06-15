package id.riverflows.core.data.source.local.room

import androidx.room.*
import id.riverflows.core.data.source.local.entity.Entity.Companion.TYPE_MOVIE
import id.riverflows.core.data.source.local.entity.Entity.Companion.TYPE_TV
import id.riverflows.core.data.source.local.entity.Entity.MovieTv
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {
    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE")
    fun getMovies(): Flow<List<MovieTv>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND id = :id")
    fun getDetailMovie(id: Long): Flow<MovieTv>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV")
    fun getTvShows(): Flow<List<MovieTv>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND id = :id")
    fun getDetailTv(id: Long): Flow<MovieTv>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<MovieTv>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: MovieTv)

    @Update
    suspend fun updateData(data: MovieTv)


    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_MOVIE AND is_favorite = TRUE")
    fun getFavoriteMovies(): Flow<List<MovieTv>>

    @Query("SELECT * FROM movies_tv_shows WHERE type = $TYPE_TV AND is_favorite = TRUE")
    fun getFavoriteTvShows(): Flow<List<MovieTv>>

    @Query("SELECT * FROM movies_tv_shows WHERE id = :id AND type = $TYPE_MOVIE AND is_favorite = TRUE")
    fun getFavoriteMovie(id: Long): Flow<MovieTv?>

    @Query("SELECT * FROM movies_tv_shows WHERE id = :id AND type = $TYPE_TV AND is_favorite = TRUE")
    fun getFavoriteTv(id: Long): Flow<MovieTv?>
}