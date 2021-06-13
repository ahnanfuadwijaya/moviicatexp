package id.riverflows.core.data

import id.riverflows.core.data.source.local.LocalDataSource
import id.riverflows.core.data.source.remote.RemoteDataSource
import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.response.MovieDetailResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.domain.model.*
import id.riverflows.core.domain.repository.IFavoriteRepository
import id.riverflows.core.domain.repository.IMovieRepository
import id.riverflows.core.domain.repository.ITvRepository
import id.riverflows.core.utils.AppExecutors
import id.riverflows.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
@Singleton
class MoviiCatRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository, ITvRepository, IFavoriteRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovies().map {
                    DataMapper.mapMovieDetailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                localDataSource.insertMovies(DataMapper.mapMoviesResponseToMovieDetailEntities(data))
            }
        }.asFlow()

    override fun getDetailMovie(id: Long): Flow<Resource<MovieDetail>> =
        object : NetworkBoundResource<MovieDetail, MovieDetailResponse>(){
            override fun loadFromDB(): Flow<MovieDetail> {
                return localDataSource.getDetailMovie(id).map {
                    DataMapper.mapMovieDetailEntityToMovieDetailDomain(it)
                }
            }

            override fun shouldFetch(data: MovieDetail?): Boolean = data == null

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                localDataSource.insertMovie(DataMapper.mapMovieDetailResponseToMovieDetailEntity(data))
            }
        }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovie(id: Long): Flow<Favorite> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTvShows(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTvShow(id: Long): Flow<Favorite> {
        TODO("Not yet implemented")
    }

    override fun setFavorite(data: Favorite) {
        TODO("Not yet implemented")
    }


    override fun getTvShows(): Flow<Resource<List<Tv>>> {
        TODO("Not yet implemented")
    }

    override fun getDetailTv(id: Long): Flow<Resource<TvDetail>> {
        TODO("Not yet implemented")
    }
}