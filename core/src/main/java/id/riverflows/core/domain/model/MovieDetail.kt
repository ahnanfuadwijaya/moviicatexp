package id.riverflows.core.domain.model

data class MovieDetail (
    val id: Long,
    val title: String,
    val voteAverage: Float,
    val releaseDate: String,
    val posterPath: String,
    val overview: String?,
    val popularity: Float?,
    val status: String?
)