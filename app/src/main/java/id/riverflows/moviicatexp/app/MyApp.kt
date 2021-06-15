package id.riverflows.moviicatexp.app

import android.app.Application
import id.riverflows.core.BuildConfig
import id.riverflows.core.di.databaseModule
import id.riverflows.core.di.networkModule
import id.riverflows.core.di.repositoryModule
import id.riverflows.moviicatexp.di.appViewModelModule
import id.riverflows.moviicatexp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
open class MyApp: Application(){
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
