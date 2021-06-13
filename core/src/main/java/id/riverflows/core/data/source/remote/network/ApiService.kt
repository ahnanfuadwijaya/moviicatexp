package id.riverflows.core.data.source.remote.network

import id.riverflows.core.data.source.remote.response.MovieDetailResponse
import id.riverflows.core.data.source.remote.response.MoviesResponse
import id.riverflows.core.data.source.remote.response.TvDetailResponse
import id.riverflows.core.data.source.remote.response.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("3/discover/movie")
    suspend fun getMovies(): MoviesResponse

    @GET("3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Long
    ): MovieDetailResponse

    @GET("3/discover/tv")
    suspend fun getTvShows(): TvShowsResponse

    @GET("3/tv/{id}")
    suspend fun getTvDetail(
        @Path("id") id: Long
    ): TvDetailResponse
}
