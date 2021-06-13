package id.riverflows.core.data.source.local.room

import androidx.room.*
import id.riverflows.core.data.source.local.entity.MovieDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_detail")
    fun getMovies(): Flow<List<MovieDetailEntity>>

    @Query("SELECT * FROM movies_detail WHERE id = :id")
    fun getDetailMovie(id: Long): Flow<MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(data: MovieDetailEntity)

    @Update
    fun updateMovie(data: MovieDetailEntity)
}