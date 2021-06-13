package id.riverflows.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String
)