package id.riverflows.core.data.source.remote.network

import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.MovieResponse.Movies
import id.riverflows.core.data.source.remote.response.TvResponse
import id.riverflows.core.data.source.remote.response.TvResponse.TvShows
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/discover/movie")
    suspend fun getMovies(): Movies

    @GET("3/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Long
    ): MovieResponse.Detail

    @GET("3/discover/tv")
    suspend fun getTvShows(): TvShows

    @GET("3/tv/{id}")
    suspend fun getTvDetail(
        @Path("id") id: Long
    ): TvResponse.Detail

    @GET("/3/search/movie")
    suspend fun getMoviesSearchResult(
        @Query("query")query: String,
        @Query("page")page: Long
    ): Movies
    @GET("/3/search/tv")
    suspend fun getTvShowsSearchResult(
        @Query("query")query: String,
        @Query("page")page: Long
    ): TvShows
}
