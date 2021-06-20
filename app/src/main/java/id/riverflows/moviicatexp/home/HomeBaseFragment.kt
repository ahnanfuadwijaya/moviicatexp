package id.riverflows.moviicatexp.home

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
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
abstract class HomeBaseFragment: Fragment(), GridRvAdapter.OnItemClickCallback, SearchView.OnQueryTextListener {
    protected val viewModel: HomeSharedViewModel by viewModel()
    private val rvAdapter = GridRvAdapter()
    private val searchView: SearchView by lazy {
        (activity as HomeActivity).findViewById(R.id.search_view)
    }
    private val clearSearchResultButton: ImageView by lazy {
        searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
    }
    private var _binding: FragmentListContainerBinding? = null
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

    private fun setupView(){
        rvAdapter.setOnItemClickCallback(this)
        with(binding?.rvList){
            this?.setHasFixedSize(true)
            this?.layoutManager = GridLayoutManager(context, AppConfig.GRID_ITEM_COUNT)
            this?.addItemDecoration(SpaceItemDecoration(AppConfig.SPACE_ITEM_DECORATION))
            this?.adapter = rvAdapter
        }

        searchView.setOnQueryTextListener(this)

        clearSearchResultButton.setOnClickListener {
            if(searchView.query.isEmpty()) {
                searchView.isIconified = true
            } else {
                searchView.setQuery("", false)
            }
            requestData()
        }
    }

    protected fun setState(state: State){
        Timber.d(state.toString())
        when(state){
            State.LOADING -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.visibility = View.VISIBLE
                binding?.viewLoadingShimmer?.startShimmer()
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            State.SUCCESS -> {
                binding?.rvList?.visibility = View.VISIBLE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            State.NO_DATA -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.VISIBLE
            }
            State.ERROR -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
        }
    }

    protected fun setList(data: List<MovieTv>){
        rvAdapter.setList(data)
    }

    override fun onItemClicked(data: MovieTv) {
        moveToDetail(data)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvList?.adapter = null
        _binding = null
    }

    private fun search(query: String?){
        lifecycleScope.launch(Dispatchers.IO) {
            query?.run { viewModel.queryChannel.send(this) }
        }
    }

    abstract fun observeViewModel()
    abstract fun requestData()
    abstract fun moveToDetail(data: MovieTv)
}