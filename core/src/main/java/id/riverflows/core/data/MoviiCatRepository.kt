package id.riverflows.core.data

import id.riverflows.core.data.source.local.LocalDataSource
import id.riverflows.core.data.source.remote.RemoteDataSource
import id.riverflows.core.data.source.remote.network.ApiResponse
import id.riverflows.core.data.source.remote.response.MovieResponse
import id.riverflows.core.data.source.remote.response.TvResponse
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.domain.repository.IMovieTvRepository
import id.riverflows.core.utils.DataMapper
import kotlinx.coroutines.flow.*

@Suppress("SpellCheckingInspection")
class MoviiCatRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
): IMovieTvRepository {
    override fun getMovies(): Flow<Resource<List<MovieTv>>> =
        object : NetworkBoundResource<List<MovieTv>, List<MovieResponse.Item>>(){
            override fun loadFromDB(): Flow<List<MovieTv>> {
                return localDataSource.getMovies().map {
                    DataMapper.mapEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<MovieTv>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse.Item>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse.Item>) {
                localDataSource.insertList(DataMapper.mapMoviesResponseToEntities(data))
            }
        }.asFlow()

    override fun getDetailMovie(id: Long): Flow<Resource<MovieTv>> =
        object : NetworkBoundResource<MovieTv, MovieResponse.Detail>(){
            override fun loadFromDB(): Flow<MovieTv?> {
                return localDataSource.getDetailMovie(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: MovieTv?): Boolean{
                if(data == null) return true
                return isDetailDataObtained(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse.Detail>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: MovieResponse.Detail) {
                localDataSource.insertData(DataMapper.mapMovieDetailResponseToEntity(data))
            }
        }.asFlow()

    override fun getTvShows(): Flow<Resource<List<MovieTv>>> =
        object : NetworkBoundResource<List<MovieTv>, List<TvResponse.Item>>(){
            override fun loadFromDB(): Flow<List<MovieTv>> {
                return localDataSource.getTvShows().map {
                    DataMapper.mapEntitiesToDomains(it)
                }
            }

            override fun shouldFetch(data: List<MovieTv>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvResponse.Item>>> {
                return remoteDataSource.getTvShows()
            }

            override suspend fun saveCallResult(data: List<TvResponse.Item>) {
                localDataSource.insertList(DataMapper.mapTvShowsResponseToEntities(data))
            }
        }.asFlow()

    override fun getDetailTv(id: Long): Flow<Resource<MovieTv>> =
        object : NetworkBoundResource<MovieTv, TvResponse.Detail>(){
            override fun loadFromDB(): Flow<MovieTv?> {
                return localDataSource.getDetailTv(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: MovieTv?): Boolean{
                if(data == null) return true
                return isDetailDataObtained(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<TvResponse.Detail>> {
                return remoteDataSource.getDetailTv(id)
            }

            override suspend fun saveCallResult(data: TvResponse.Detail) {
                localDataSource.insertData(DataMapper.mapTvDetailResponseToEntity(data))
            }
        }.asFlow()

    override suspend fun updateData(data: MovieTv) {
        localDataSource.insertData(DataMapper.mapDomainToEntity(data))
    }

    override fun getFavoriteMovies(): Flow<List<MovieTv>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    override fun getFavoriteTvShows(): Flow<List<MovieTv>> {
        return localDataSource.getFavoriteTvShows().map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    override suspend fun searchMovies(query: String, page: Long): Flow<Resource<List<MovieTv>>>{
        return flow{
            emit(Resource.Loading())
            when(val response = remoteDataSource.getMoviesSearchResult(query, page).first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMoviesResponseToDomains(response.data)
                    val flowData = flowOf(Resource.Success(data))
                    emitAll(flowData)
                }
                is ApiResponse.Empty -> {
                    val list = emptyList<MovieTv>()
                    emitAll(flowOf(Resource.Success(list)))
                }
                is ApiResponse.Error -> {
                    @Suppress("RemoveExplicitTypeArguments")
                    emit(Resource.Error<List<MovieTv>>(response.errorMessage))
                }
            }
        }
    }

    override suspend fun searchTvShows(query: String, page: Long): Flow<Resource<List<MovieTv>>> {
        return flow{
            emit(Resource.Loading())
            when(val response = remoteDataSource.getTvShowsSearchResult(query, page).first()){
                is ApiResponse.Success -> {
                    val data = DataMapper.mapTvShowsResponseToDomains(response.data)
                    val flowData = flowOf(Resource.Success(data))
                    emitAll(flowData)
                }
                is ApiResponse.Empty -> {
                    val list = emptyList<MovieTv>()
                    emitAll(flowOf(Resource.Success(list)))
                }
                is ApiResponse.Error -> {
                    @Suppress("RemoveExplicitTypeArguments")
                    emit(Resource.Error<List<MovieTv>>(response.errorMessage))
                }
            }
        }
    }

    override fun searchFavoriteMovies(query: String): Flow<List<MovieTv>> {
        return localDataSource.getFavoriteMoviesSearchResult(query).map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    override fun searchFavoriteTvShows(query: String): Flow<List<MovieTv>> {
        return localDataSource.getFavoriteTvShowsSearchResult(query).map {
            DataMapper.mapEntitiesToDomains(it)
        }
    }

    internal fun isDetailDataObtained(data: MovieTv): Boolean{
        return data.overview == null || data.popularity == null || data.status == null
    }
}