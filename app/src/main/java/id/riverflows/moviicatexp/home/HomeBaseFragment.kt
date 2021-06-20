package id.riverflows.moviicatexp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig
import id.riverflows.core.utils.State
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
abstract class HomeBaseFragment: Fragment(), GridRvAdapter.OnItemClickCallback, SearchView.OnQueryTextListener {

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

    override fun onResume() {
        super.onResume()
        searchView.setQuery("", false)
        if(!searchView.isIconified){
            searchView.onActionViewCollapsed()
        }
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

    override fun onItemClicked(id: Long) {
        moveToDetail(id)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.run { if(this.isNotBlank()) search(this) else requestData()}
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.run { if(this.isNotBlank()) search(this) else requestData()}
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvList?.adapter = null
        _binding = null
    }

    abstract fun observeViewModel()
    abstract fun requestData()
    abstract fun moveToDetail(id: Long)
    abstract fun search(query: String?)
}