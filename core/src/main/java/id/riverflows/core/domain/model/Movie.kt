package id.riverflows.core.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val voteAverage: Float,
    val releaseDate: String,
    val posterPath: String
)
