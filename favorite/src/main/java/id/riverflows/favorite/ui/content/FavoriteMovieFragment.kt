package id.riverflows.favorite.ui.content

import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteMovieFragment: FavoriteFragment() {
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
        viewModel.getFavoriteMovies()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.moviesQueryChannel.send(query.toString())
        }
    }
}