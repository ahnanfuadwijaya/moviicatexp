package id.riverflows.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig
import id.riverflows.core.utils.State
import id.riverflows.core.utils.State.NO_DATA
import id.riverflows.core.utils.State.SUCCESS
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeActivity
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteContentFragment(
    private val type: Int
) : Fragment(), GridRvAdapter.OnItemClickCallback {
    private var _binding: FragmentListContainerBinding? = null
    private val binding
        get() = _binding
    private val viewModel: FavoriteViewModel by viewModel()
    private val rvAdapter = GridRvAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListContainerBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
        requestData()
    }

    private fun requestData(){
        if(type == TYPE_MOVIE){
            viewModel.getFavoriteMovies()
        }else{
            viewModel.getFavoriteTvShows()
        }
        Timber.d("$type")
    }

    private fun setupView(){
        with(binding?.rvList){
            this?.setHasFixedSize(true)
            this?.layoutManager = GridLayoutManager(context, AppConfig.GRID_ITEM_COUNT)
            this?.addItemDecoration(SpaceItemDecoration(AppConfig.SPACE_ITEM_DECORATION))
            this?.adapter = rvAdapter
        }
        rvAdapter.setOnItemClickCallback(this)
        val searchView: SearchView = (activity as HomeActivity).findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })

        val clearSearchResultButton: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        clearSearchResultButton.setOnClickListener {
            if(searchView.query.isEmpty()) {
                searchView.isIconified = true
            } else {
                searchView.setQuery("", false)
            }
            requestData()
        }
    }

    internal fun search(query: String?){
        Timber.d("Search $type")
        if(type == TYPE_MOVIE){
            lifecycleScope.launch {
                viewModel.moviesQueryChannel.send(query.toString())
            }
        }else{
            lifecycleScope.launch {
                viewModel.tvShowsQueryChannel.send(query.toString())
            }
        }
    }

    private fun observeViewModel(){
        if(type == TYPE_MOVIE){
            viewModel.movies.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    setState(NO_DATA)
                } else {
                    setState(SUCCESS)
                    bindData(it)
                }
                Timber.d("$type, $it")
            }
            viewModel.moviesResult.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    setState(NO_DATA)
                } else {
                    setState(SUCCESS)
                    bindData(it)
                }
                Timber.d("$type, $it")
            }
        }else{
            viewModel.tvShows.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    setState(NO_DATA)
                } else {
                    setState(SUCCESS)
                    bindData(it)
                }
                Timber.d("$type, $it")
            }
            viewModel.tvShowsResult.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    setState(NO_DATA)
                } else {
                    setState(SUCCESS)
                    bindData(it)
                }
                Timber.d("$type, $it")
            }
        }
    }

    private fun bindData(list: List<MovieTv>){
        rvAdapter.setList(list)
        Timber.d(list.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvList?.adapter = null
        _binding = null
    }

    override fun onItemClicked(data: MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_TV_DATA, data)
            }
        )
    }

    private fun setState(state: State){
        if(state==SUCCESS){
            binding?.rvList?.visibility = View.VISIBLE
            binding?.viewLoadingShimmer?.stopShimmer()
            binding?.viewLoadingShimmer?.visibility = View.GONE
            binding?.viewNoData?.root?.visibility = View.GONE
        }
        else if(state == NO_DATA){
            binding?.rvList?.visibility = View.GONE
            binding?.viewLoadingShimmer?.stopShimmer()
            binding?.viewLoadingShimmer?.visibility = View.GONE
            binding?.viewNoData?.root?.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: Int) = FavoriteContentFragment(type)
    }
}