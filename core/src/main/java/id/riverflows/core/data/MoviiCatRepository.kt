package id.riverflows.core.data

import id.riverflows.core.data.source.local.LocalDataSource
import id.riverflows.core.data.source.remote.RemoteDataSource
import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import id.riverflows.core.domain.model.Content
import id.riverflows.core.domain.repository.IMovieTvRepository
import id.riverflows.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("SpellCheckingInspection")
class MoviiCatRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
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
                return remoteDataSource.getMovies()
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

            override fun shouldFetch(data: Content.MovieTv?): Boolean{
                if(data == null) return true
                return isDetailDataObtained(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse.Detail>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: MovieResponse.Detail) {
                localDataSource.updateData(DataMapper.mapMovieDetailResponseToEntity(data))
            }
        }.asFlow()

    override fun getTvShows(): Flow<Resource<List<Content.MovieTv>>> =
        object : NetworkBoundResource<List<Content.MovieTv>, List<TvResponse.Item>>(){
            override fun loadFromDB(): Flow<List<Content.MovieTv>> {
                return localDataSource.getTvShows().map {
                    DataMapper.mapEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<Content.MovieTv>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse.Item>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvResponse.Item>) {
                localDataSource.insertList(DataMapper.mapTvShowsResponseToEntities(data))
            }
        }.asFlow()

    override fun getDetailTv(id: Long): Flow<Resource<Content.MovieTv>> =
        object : NetworkBoundResource<Content.MovieTv, TvResponse.Detail>(){
            override fun loadFromDB(): Flow<Content.MovieTv> {
                return localDataSource.getDetailTv(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Content.MovieTv?): Boolean{
                if(data == null) return true
                return isDetailDataObtained(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<TvResponse.Detail>> {
                return remoteDataSource.getDetailTv(id)
            }

            override suspend fun saveCallResult(data: TvResponse.Detail) {
                localDataSource.updateData(DataMapper.mapTvDetailResponseToEntity(data))
            }
        }.asFlow()

    override suspend fun updateData(data: Content.MovieTv) {
        localDataSource.updateData(DataMapper.mapDomainToEntity(data))
    }

    override fun getFavoriteMovies(): Flow<List<Content.MovieTv>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    override fun getFavoriteTvShows(): Flow<List<Content.MovieTv>> {
        return localDataSource.getFavoriteTvShows().map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    private fun isDetailDataObtained(data: Content.MovieTv): Boolean{
        return data.overview == null || data.popularity == null || data.status == null
    }
}