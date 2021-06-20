package id.riverflows.moviicatexp.home.movie

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
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig.GRID_ITEM_COUNT
import id.riverflows.core.utils.AppConfig.SPACE_ITEM_DECORATION
import id.riverflows.core.utils.State
import id.riverflows.core.utils.State.*
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeActivity
import id.riverflows.moviicatexp.home.HomeSharedViewModel
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import id.riverflows.moviicatexp.utils.Utils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class MovieFragment : Fragment(), GridRvAdapter.OnItemClickCallback {
    internal val viewModel: HomeSharedViewModel by viewModel()
    private val rvAdapter = GridRvAdapter()
    private var _binding: FragmentListContainerBinding? = null
    private var isSearching = false
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListContainerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
        requestData()
    }

    private fun requestData(){
        viewModel.getMovies()
    }

    private fun setupView(){
        rvAdapter.setOnItemClickCallback(this)
        with(binding?.rvList){
            this?.setHasFixedSize(true)
            this?.layoutManager = GridLayoutManager(context, GRID_ITEM_COUNT)
            this?.addItemDecoration(SpaceItemDecoration(SPACE_ITEM_DECORATION))
            this?.adapter = rvAdapter
        }
        val searchView: SearchView = (activity as HomeActivity).findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(query.toString())
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(newText.toString())
                }
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

    private fun observeViewModel(){
        viewModel.movies.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    if(!isSearching) setState(LOADING)
                }
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                    it.data?.let { data ->
                        if(data.isEmpty()) {
                            setState(NO_DATA)
                        } else {
                            setState(SUCCESS)
                            rvAdapter.setList(data)
                        }
                    }
                }
                is Resource.Error -> {
                    setState(ERROR)
                    binding?.let { view ->
                        Utils.showIndefiniteSnackBar(view.root, it.message.toString())
                    }
                }
            }
        }

        viewModel.searchMoviesResult.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    setState(LOADING)
                }
                is Resource.Success -> {
                    Timber.d(it.data.toString())
                    it.data?.let { data ->
                        if(data.isEmpty()) {
                            setState(NO_DATA)
                        } else {
                            setState(SUCCESS)
                            rvAdapter.setList(data)
                        }
                    }
                }
                is Resource.Error -> {
                    setState(ERROR)
                    binding?.let { view ->
                        Utils.showIndefiniteSnackBar(view.root, it.message.toString())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding?.rvList?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    override fun onItemClicked(data: MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_TV_DATA, data)
            }
        )
    }

    private fun setState(state: State){
        when(state){
            LOADING -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.visibility = View.VISIBLE
                binding?.viewLoadingShimmer?.startShimmer()
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            SUCCESS -> {
                isSearching = false
                binding?.rvList?.visibility = View.VISIBLE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            NO_DATA -> {
                isSearching = false
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.VISIBLE
            }
            ERROR -> {
                isSearching = false
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
        }
    }
}