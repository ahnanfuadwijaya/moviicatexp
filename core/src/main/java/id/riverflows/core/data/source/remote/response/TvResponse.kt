package id.riverflows.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvResponse(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("vote_average")
    val voteAverage: Float,
    @field:SerializedName("first_air_date")
    val firstAirDate: String,
    @field:SerializedName("poster_path")
    val posterPath: String
)
