package id.riverflows.core.utils

import id.riverflows.core.data.source.local.entity.MovieTvEntity
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.core.utils.UtilConstants.TYPE_TV

object DataMapper {
    fun mapEntitiesToDomains(input: List<MovieTvEntity>): List<MovieTv>{
        return input.map {
            MovieTv(
                it.id,
                it.title,
                it.voteAverage,
                it.releaseDate,
                it.posterPath,
                it.lastDate,
                it.overview,
                it.popularity,
                it.status,
                it.type,
                it.isFavorite
            )
        }
    }

    fun mapMoviesResponseToEntities(input: List<MovieResponse.Item>): List<MovieTvEntity>{
        return input.map {
            MovieTvEntity(
                it.id,
                it.title,
                it.voteAverage,
                it.releaseDate,
                it.posterPath,
                null,
                null,
                null,
                null,
                TYPE_MOVIE,
            )
        }
    }

    fun mapTvShowsResponseToEntities(input: List<TvResponse.Item>): List<MovieTvEntity>{
        return input.map {
            MovieTvEntity(
                it.id,
                it.name,
                it.voteAverage,
                it.firstAirDate,
                it.posterPath,
                null,
                null,
                null,
                null,
                TYPE_TV
            )
        }
    }

    fun mapEntityToDomain(input: MovieTvEntity): MovieTv{
        return MovieTv(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            input.lastDate,
            input.overview,
            input.popularity,
            input.status,
            input.type,
            input.isFavorite
        )
    }

    fun mapMovieDetailResponseToEntity(input: MovieResponse.Detail): MovieTvEntity{
        return MovieTvEntity(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            null,
            input.overview,
            input.popularity,
            input.status,
            TYPE_MOVIE,
        )
    }

    fun mapTvDetailResponseToEntity(input: TvResponse.Detail): MovieTvEntity{
        return MovieTvEntity(
            input.id,
            input.name,
            input.voteAverage,
            input.firstAirDate,
            input.posterPath,
            input.lastAirDate,
            input.overview,
            input.popularity,
            input.status,
            TYPE_TV,
        )
    }

    fun mapDomainToEntity(input: MovieTv): MovieTvEntity{
        return MovieTvEntity(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            input.lastDate,
            input.overview,
            input.popularity,
            input.status,
            input.type,
            input.isFavorite
        )
    }
}