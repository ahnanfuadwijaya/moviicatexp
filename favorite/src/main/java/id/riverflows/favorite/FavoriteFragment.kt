package id.riverflows.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import id.riverflows.favorite.databinding.FragmentFavoriteBinding
import id.riverflows.favorite.di.favoriteViewModelModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding
    private val viewModel: FavoriteViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(favoriteViewModelModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.favoriteMovies.collectLatest {
                Timber.d(it.toString())
            }
        }
    }
}