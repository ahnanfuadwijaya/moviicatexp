package id.riverflows.core.domain.model

data class MovieTv(
    val id: Long,
    val title: String,
    val voteAverage: Float,
    val releaseDate: String?,
    val posterPath: String?,
    val lastDate: String?,
    val overview: String?,
    val popularity: Float?,
    val status: String?,
    val type: Int = 1,
    var isFavorite: Boolean = false
)