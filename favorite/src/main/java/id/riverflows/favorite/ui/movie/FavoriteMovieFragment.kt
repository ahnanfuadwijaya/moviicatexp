package id.riverflows.favorite.ui.movie

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import id.riverflows.core.utils.State
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
        viewModel.getFavoriteMovies()
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.moviesQueryChannel.send(query.toString())
        }
    }

    override fun moveToDetail(id: Long) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_TYPE, UtilConstants.TYPE_MOVIE)
                putExtra(UtilConstants.EXTRA_ID, id)
            }
        )
    }
}