package id.riverflows.moviicatexp.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.riverflows.core.data.Resource
import id.riverflows.moviicatexp.databinding.ActivityHomeBinding
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel(){
        homeViewModel.movies.observe(this){
            when(it){
                is Resource.Loading ->{}
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                }
                is Resource.Error -> {
                    Timber.d(it.toString())
                }
            }
        }
    }
}