package id.riverflows.moviicatexp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}