package id.riverflows.favorite.ui.tv

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State.NO_DATA
import id.riverflows.core.utils.State.SUCCESS
import id.riverflows.core.utils.UtilConstants
import id.riverflows.favorite.ui.FavoriteFragment
import id.riverflows.moviicatexp.detail.DetailActivity
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
        viewModel.getFavoriteTvShows()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.tvShowsQueryChannel.send(query.toString())
        }
    }

    override fun moveToDetail(id: Long) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_TYPE, UtilConstants.TYPE_TV)
                putExtra(UtilConstants.EXTRA_ID, id)
            }
        )
    }
}