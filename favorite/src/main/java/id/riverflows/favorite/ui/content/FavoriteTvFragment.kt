package id.riverflows.favorite.ui.content

import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteTvFragment: FavoriteFragment() {
    override fun observeViewModel() {
        viewModel.tvShows.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(State.NO_DATA)
            } else {
                setState(State.SUCCESS)
                bindData(it)
            }
        }
        viewModel.tvShowsResult.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(State.NO_DATA)
            } else {
                setState(State.SUCCESS)
                bindData(it)
            }
        }
    }

    override fun requestData() {
        viewModel.getFavoriteTvShows()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.tvShowsQueryChannel.send(query.toString())
        }
    }
}