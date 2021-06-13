package id.riverflows.core.utils

import id.riverflows.core.data.source.local.entity.MovieDetailEntity
import id.riverflows.core.data.source.remote.response.MovieDetailResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.domain.model.Movie
import id.riverflows.core.domain.model.MovieDetail

object DataMapper {
    fun mapMovieDetailEntityToDomain(input: List<MovieDetailEntity>): List<Movie>{
        return input.map {
            Movie(
                it.id,
                it.title,
                it.voteAverage,
                it.releaseDate,
                it.posterPath
            )
        }
    }

    fun mapMoviesResponseToMovieDetailEntities(input: List<MovieResponse>): List<MovieDetailEntity>{
        return input.map {
            MovieDetailEntity(
                it.id,
                it.title,
                it.voteAverage,
                it.releaseDate,
                it.posterPath,
                null,
                null,
                null
            )
        }
    }

    fun mapMovieDetailEntityToMovieDetailDomain(input: MovieDetailEntity): MovieDetail{
        return MovieDetail(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            input.overview,
            input.popularity,
            input.status
        )
    }

    fun mapMovieDetailResponseToMovieDetailEntity(input: MovieDetailResponse): MovieDetailEntity{
        return MovieDetailEntity(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            input.overview,
            input.popularity,
            input.status
        )
    }
}