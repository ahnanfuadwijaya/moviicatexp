package id.riverflows.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class TvResponse{
    data class TvShows(
        @field:SerializedName("results")
        val data: List<Item>
    )

    data class Item(
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

    data class Detail(
        @field:SerializedName("id")
        val id: Long,
        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("vote_average")
        val voteAverage: Float,
        @field:SerializedName("first_air_date")
        val firstAirDate: String,
        @field:SerializedName("poster_path")
        val posterPath: String,
        @field:SerializedName("overview")
        val overview: String?,
        @field:SerializedName("popularity")
        val popularity: Float?,
        @field:SerializedName("last_air_date")
        val lastAirDate: String?,
        @field:SerializedName("status")
        val status: String?
    )
}
