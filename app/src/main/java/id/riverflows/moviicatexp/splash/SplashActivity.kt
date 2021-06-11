package id.riverflows.moviicatexp.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}