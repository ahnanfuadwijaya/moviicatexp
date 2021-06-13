package id.riverflows.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import id.riverflows.core.domain.model.Genre

@Entity(tableName = "movies_detail")
data class MovieDetailEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Float?,
    @ColumnInfo(name = "status")
    val status: String?
)