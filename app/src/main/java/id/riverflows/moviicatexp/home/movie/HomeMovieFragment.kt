package id.riverflows.moviicatexp.home.movie

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.State
import id.riverflows.core.utils.UtilConstants
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeBaseFragment
import id.riverflows.moviicatexp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class HomeMovieFragment: HomeBaseFragment() {
    private val viewModel: HomeMovieViewModel by viewModel()

    override fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    setState(State.LOADING)
                }
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                    it.data?.let { data ->
                        if(data.isEmpty()) {
                            setState(State.NO_DATA)
                        } else {
                            setState(State.SUCCESS)
                            setList(data)
                        }
                    }
                }
                is Resource.Error -> {
                    setState(State.ERROR)
                    view?.run {
                        Utils.showIndefiniteSnackBar(this, it.message.toString(), )
                    }
                }
            }
        }

        viewModel.searchMoviesResult.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    setState(State.LOADING)
                }
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                    it.data?.let { data ->
                        if(data.isEmpty()) {
                            setState(State.NO_DATA)
                        } else {
                            setState(State.SUCCESS)
                            setList(data)
                        }
                    }
                }
                is Resource.Error -> {
                    setState(State.ERROR)
                    view?.run {
                        Utils.showIndefiniteSnackBar(this, it.message.toString())
                    }
                }
            }
        }
    }

    override fun requestData() {
        viewModel.getMovies()
    }

    override fun search(query: String?){
        lifecycleScope.launch(Dispatchers.IO) {
            query?.run { viewModel.queryChannel.send(this) }
        }
    }

    override fun moveToDetail(data: MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_MOVIE_TV_DATA, data)
            }
        )
    }
}