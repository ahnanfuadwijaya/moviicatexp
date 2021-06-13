package id.riverflows.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Content {
    @Parcelize
    data class MovieTv(
        val id: Long,
        val title: String,
        val voteAverage: Float,
        val releaseDate: String,
        val posterPath: String,
        val lastDate: String?,
        val overview: String?,
        val popularity: Float?,
        val status: String?,
        val type: Int = TYPE_MOVIE,
        val isFavorite: Boolean = false
    ): Parcelable

    companion object{
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }
}