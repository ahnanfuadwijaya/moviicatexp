package id.riverflows.core.domain.model

data class Favorite(
    val id: Long,
    val referenceId: Long,
    val title: String,
    val voteAverage: Float,
    val date: String,
    val posterPath: String,
    val type: Int
)
