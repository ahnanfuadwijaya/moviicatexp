package id.riverflows.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowsResponse(
    @field:SerializedName("results")
    val data: List<TvResponse>
)