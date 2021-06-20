package id.riverflows.favorite.ui.movie

import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State
import id.riverflows.favorite.ui.FavoriteFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteMovieFragment: FavoriteFragment() {
    private val viewModel: FavoriteMovieViewModel by viewModel()

    override fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(State.NO_DATA)
            } else {
                setState(State.SUCCESS)
                bindData(it)
            }
        }
        viewModel.moviesResult.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(State.NO_DATA)
            } else {
                setState(State.SUCCESS)
                bindData(it)
            }
        }
    }

    override fun requestData() {
        Timber.d("Request Data")
        setState(State.LOADING)
        viewModel.getFavoriteMovies()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.moviesQueryChannel.send(query.toString())
        }
    }
}