package id.riverflows.favorite.ui.tv

import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State.*
import id.riverflows.favorite.ui.FavoriteFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteTvFragment: FavoriteFragment() {
    private val viewModel: FavoriteTvViewModel by viewModel()
    override fun observeViewModel() {
        viewModel.tvShows.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(NO_DATA)
            } else {
                setState(SUCCESS)
                bindData(it)
            }
        }
        viewModel.tvShowsResult.observe(viewLifecycleOwner){
            if(it.isEmpty()) {
                setState(NO_DATA)
            } else {
                setState(SUCCESS)
                bindData(it)
            }
        }
    }

    override fun requestData() {
        Timber.d("Request Data")
        setState(LOADING)
        viewModel.getFavoriteTvShows()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.tvShowsQueryChannel.send(query.toString())
        }
    }
}