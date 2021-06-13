package id.riverflows.core.data.source.local.room

import androidx.room.*
import id.riverflows.core.data.source.local.entity.MovieDetailEntity
import id.riverflows.core.data.source.local.entity.TvDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {
    @Query("SELECT * FROM tv_shows_detail")
    fun getTvShows(): Flow<List<TvDetailEntity>>

    @Query("SELECT * FROM tv_shows_detail WHERE id = :id")
    fun getDetailTv(id: Long): Flow<TvDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(list: List<TvDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(data: TvDetailEntity)

    @Update
    fun updateTv(data: TvDetailEntity)
}