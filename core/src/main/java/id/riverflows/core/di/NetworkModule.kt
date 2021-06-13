package id.riverflows.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.riverflows.core.BuildConfig
import id.riverflows.core.data.source.remote.network.ApiService
import id.riverflows.core.utils.AppConfig.BASE_URL
import id.riverflows.core.utils.AppConfig.CONNECT_TIME_OUT
import id.riverflows.core.utils.AppConfig.READ_TIME_OUT
import id.riverflows.core.utils.AppConfig.TMDB_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideInterceptor(): Interceptor{
        return Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Connection", "close")
                    .header("Accept-Encoding", "identity")
                    .header("Authorization", "Bearer ${if(TMDB_TOKEN.isNotBlank()) TMDB_TOKEN else BuildConfig.TMDB_TOKEN}")
                    .build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}