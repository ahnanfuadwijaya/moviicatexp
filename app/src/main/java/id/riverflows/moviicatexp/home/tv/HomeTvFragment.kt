package id.riverflows.moviicatexp.home.tv

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import id.riverflows.core.data.Resource
import id.riverflows.core.utils.State
import id.riverflows.core.utils.UtilConstants.EXTRA_ID
import id.riverflows.core.utils.UtilConstants.EXTRA_TYPE
import id.riverflows.core.utils.UtilConstants.TYPE_TV
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeBaseFragment
import id.riverflows.moviicatexp.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class HomeTvFragment: HomeBaseFragment() {
    private val viewModel: HomeTvViewModel by viewModel()
    override fun requestData() {
        viewModel.getTvShows()
    }

    override fun observeViewModel() {
        viewModel.tvShows.observe(viewLifecycleOwner){
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
                    Timber.d(it.toString())
                }
            }
        }
        viewModel.searchTvShowsResult.observe(viewLifecycleOwner){
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

    override fun moveToDetail(id: Long) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, id)
                putExtra(EXTRA_TYPE, TYPE_TV)
            }
        )
    }

    override fun search(query: String?) {
        lifecycleScope.launch {
            viewModel.queryChannel.send(query.toString())
        }
    }
}