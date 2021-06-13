package id.riverflows.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Favorite {
    @Entity(tableName = "favorites")
    data class FavoriteEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "reference_id")
        val referenceId: Long,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "vote_average")
        val voteAverage: Float,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "poster_path")
        val posterPath: String,
        @ColumnInfo(name = "type")
        val type: Int
    )

    companion object{
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }
}
