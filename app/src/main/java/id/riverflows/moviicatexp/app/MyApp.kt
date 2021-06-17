package id.riverflows.moviicatexp.app

import com.google.android.play.core.splitcompat.SplitCompatApplication
import id.riverflows.core.BuildConfig
import id.riverflows.core.di.databaseModule
import id.riverflows.core.di.networkModule
import id.riverflows.core.di.repositoryModule
import id.riverflows.moviicatexp.di.appViewModelModule
import id.riverflows.moviicatexp.di.useCaseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
@FlowPreview
@ExperimentalCoroutinesApi
open class MyApp: SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    appViewModelModule
                )
            )
        }
    }
}
