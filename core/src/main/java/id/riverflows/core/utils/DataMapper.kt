package id.riverflows.core.utils

import id.riverflows.core.data.source.local.entity.Entity
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import id.riverflows.core.domain.model.Content

object DataMapper {
    fun mapEntitiesToDomains(input: List<Entity.MovieTv>): List<Content.MovieTv>{
        return input.map {
            Content.MovieTv(
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

    fun mapMoviesResponseToEntities(input: List<MovieResponse.Item>): List<Entity.MovieTv>{
        return input.map {
            Entity.MovieTv(
                it.id,
                it.title,
                it.voteAverage,
                it.releaseDate,
                it.posterPath,
                null,
                null,
                null,
                null,
                Entity.TYPE_MOVIE,
            )
        }
    }

    fun mapTvShowsResponseToEntities(input: List<TvResponse.Item>): List<Entity.MovieTv>{
        return input.map {
            Entity.MovieTv(
                it.id,
                it.name,
                it.voteAverage,
                it.firstAirDate,
                it.posterPath,
                null,
                null,
                null,
                null,
                Entity.TYPE_TV
            )
        }
    }

    fun mapEntityToDomain(input: Entity.MovieTv): Content.MovieTv{
        return Content.MovieTv(
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

    fun mapMovieDetailResponseToEntity(input: MovieResponse.Detail): Entity.MovieTv{
        return Entity.MovieTv(
            input.id,
            input.title,
            input.voteAverage,
            input.releaseDate,
            input.posterPath,
            null,
            input.overview,
            input.popularity,
            input.status,
            Entity.TYPE_MOVIE,
        )
    }

    fun mapTvDetailResponseToEntity(input: TvResponse.Detail): Entity.MovieTv{
        return Entity.MovieTv(
            input.id,
            input.name,
            input.voteAverage,
            input.firstAirDate,
            input.posterPath,
            input.lastAirDate,
            input.overview,
            input.popularity,
            input.status,
            Entity.TYPE_TV,
        )
    }

    fun mapDomainToEntity(input: Content.MovieTv): Entity.MovieTv{
        return Entity.MovieTv(
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