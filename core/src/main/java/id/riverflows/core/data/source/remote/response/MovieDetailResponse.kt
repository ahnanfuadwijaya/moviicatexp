package id.riverflows.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse (
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("vote_average")
    val voteAverage: Float,
    @field:SerializedName("release_date")
    val releaseDate: String,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Float?,
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("is_favorite")
    var isFavorite: Boolean = false
)