package id.riverflows.moviicatexp.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.riverflows.core.utils.AppExecutors
import id.riverflows.moviicatexp.databinding.ActivitySplashBinding
import id.riverflows.moviicatexp.home.HomeActivity
import id.riverflows.moviicatexp.utils.UtilConfig.SPLASH_DURATION
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    @Inject
    lateinit var appExecutors: AppExecutors
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appExecutors.scheduled().schedule({
           startActivity(Intent(this, HomeActivity::class.java).apply {
               flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           })
        }, SPLASH_DURATION, TimeUnit.MILLISECONDS)
    }
}