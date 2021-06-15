package id.riverflows.moviicatexp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavController()
    }

    private fun setupNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment?
        val navController = navHostFragment?.navController
        with(binding.bottomNavigationView){
            if (navController != null) {
                setupWithNavController(navController)
                setOnNavigationItemSelectedListener { item ->
                    when(item.itemId){
                        R.id.menu_home -> {
                            item.isChecked = true
                            navController.navigate(R.id.nav_home)
                            true
                        }
                        R.id.menu_search -> {
                            item.isChecked = true
                            navController.navigate(R.id.nav_search)
                            true
                        }
                        R.id.menu_favorite -> {
                            item.isChecked = true
                            navController.navigate(R.id.nav_favorite)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }
}