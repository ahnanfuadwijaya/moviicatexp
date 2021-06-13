package id.riverflows.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.riverflows.core.domain.model.Content

class Entity {
    @Entity(tableName = "movies_tv_shows")
    data class MovieTv(
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
        @ColumnInfo(name = "last_date")
        val lastDate: String?,
        @ColumnInfo(name = "overview")
        val overview: String?,
        @ColumnInfo(name = "popularity")
        val popularity: Float?,
        @ColumnInfo(name = "status")
        val status: String?,
        @ColumnInfo(name = "type")
        val type: Int = TYPE_MOVIE,
        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean = false
    )

    companion object{
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }
}
