package id.riverflows.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import id.riverflows.core.domain.model.Genre

@Entity(tableName = "tv_shows_detail")
data class TvDetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Float?,
    @ColumnInfo(name = "last_air_date")
    val lastAirDate: String?,
    @ColumnInfo(name = "status")
    val status: String?
)