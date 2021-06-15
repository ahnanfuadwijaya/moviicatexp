package id.riverflows.core.di

import androidx.room.Room
import id.riverflows.core.BuildConfig
import id.riverflows.core.data.MoviiCatRepository
import id.riverflows.core.data.source.local.LocalDataSource
import id.riverflows.core.data.source.local.room.MoviiCatDatabase
import id.riverflows.core.data.source.remote.RemoteDataSource
import id.riverflows.core.data.source.remote.network.ApiService
import id.riverflows.core.domain.repository.IMovieTvRepository
import id.riverflows.core.utils.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MoviiCatDatabase>().movieTvDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MoviiCatDatabase::class.java, "moviicat.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        val interceptor = Interceptor { chain ->
            val request =
                chain.request().newBuilder()
                    .header("Connection", "close")
                    .header("Accept-Encoding", "identity")
                    .header(
                        "Authorization",
                        "Bearer ${if (AppConfig.TMDB_TOKEN.isNotBlank()) AppConfig.TMDB_TOKEN else BuildConfig.TMDB_TOKEN}"
                    )
                    .build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(AppConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.READ_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IMovieTvRepository> { MoviiCatRepository(get(), get()) }
}