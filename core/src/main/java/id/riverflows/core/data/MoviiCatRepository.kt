package id.riverflows.core.data

import id.riverflows.core.data.source.local.LocalDataSource
import id.riverflows.core.data.source.remote.RemoteDataSource
import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.domain.model.Content
import id.riverflows.core.domain.repository.IMovieTvRepository
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
): IMovieTvRepository {
    override fun getMovies(): Flow<Resource<List<Content.MovieTv>>> =
        object : NetworkBoundResource<List<Content.MovieTv>, List<MovieResponse.Item>>(){
            override fun loadFromDB(): Flow<List<Content.MovieTv>> {
                return localDataSource.getMovies().map {
                    DataMapper.mapEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Content.MovieTv>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse.Item>>> {
                return remoteDataSource.getAllMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse.Item>) {
                localDataSource.insertList(DataMapper.mapMoviesResponseToEntities(data))
            }
        }.asFlow()

    override fun getDetailMovie(id: Long): Flow<Resource<Content.MovieTv>> =
        object : NetworkBoundResource<Content.MovieTv, MovieResponse.Detail>(){
            override fun loadFromDB(): Flow<Content.MovieTv> {
                return localDataSource.getDetailMovie(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Content.MovieTv?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse.Detail>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: MovieResponse.Detail) {
                localDataSource.insertData(DataMapper.mapMovieDetailResponseToEntity(data))
            }
        }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Content.MovieTv>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovie(id: Long): Flow<Content.MovieTv> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTvShows(): Flow<List<Content.MovieTv>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTvShow(id: Long): Flow<Content.MovieTv> {
        TODO("Not yet implemented")
    }

    override fun setFavorite(data: Content.MovieTv) {
        TODO("Not yet implemented")
    }


    override fun getTvShows(): Flow<Resource<List<Content.MovieTv>>> {
        TODO("Not yet implemented")
    }

    override fun getDetailTv(id: Long): Flow<Resource<Content.MovieTv>> {
        TODO("Not yet implemented")
    }
}