package id.riverflows.core.domain.model

data class TvDetail(
    val id: Long,
    val name: String,
    val voteAverage: Float,
    val firstAirDate: String,
    val posterPath: String,
    val overview: String?,
    val popularity: Float?,
    val lastAirDate: String?,
    val status: String?
)
