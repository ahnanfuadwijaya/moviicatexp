package id.riverflows.moviicatexp.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import id.riverflows.core.utils.UtilConstants.FAVORITE_MODULE_NAME
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
                            if(installFavoriteModule()){
                                navController.navigate(R.id.nav_favorite)
                                item.isChecked = true
                                true
                            }else{
                                Toast.makeText(this@HomeActivity, getString(R.string.message_no_module), Toast.LENGTH_LONG).show()
                                false
                            }
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun installFavoriteModule(): Boolean {
        try {
            val splitInstallManager = SplitInstallManagerFactory.create(this)
            val moduleName = FAVORITE_MODULE_NAME
            var installResult = false
            if (splitInstallManager.installedModules.contains(moduleName)) {
                installResult = true
            } else {
                val request = SplitInstallRequest.newBuilder()
                    .addModule(moduleName)
                    .build()
                splitInstallManager.startInstall(request)
                    .addOnSuccessListener {
                        installResult = true
                    }
                    .addOnFailureListener {
                        installResult = false
                    }
            }
            return installResult
        }catch (exception: Exception){
            Toast.makeText(this, exception.message.toString(), Toast.LENGTH_LONG).show()
            return false
        }
    }
}