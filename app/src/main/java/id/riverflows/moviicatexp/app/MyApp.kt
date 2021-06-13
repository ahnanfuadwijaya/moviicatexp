package id.riverflows.moviicatexp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import id.riverflows.core.BuildConfig
import timber.log.Timber

@Suppress("unused")
@HiltAndroidApp
open class MyApp: Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
