package id.riverflows.favorite.ui.content

import android.content.Intent
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
import id.riverflows.core.utils.State.NO_DATA
import id.riverflows.core.utils.State.SUCCESS
import id.riverflows.core.utils.UtilConstants
import id.riverflows.favorite.ui.FavoriteViewModel
import id.riverflows.moviicatexp.R
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeActivity
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
abstract class FavoriteFragment: Fragment(), GridRvAdapter.OnItemClickCallback {
    private var _binding: FragmentListContainerBinding? = null
    private val binding
        get() = _binding
    protected val viewModel: FavoriteViewModel by viewModel()
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

    protected fun setState(state: State){
        if(state== SUCCESS){
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

    protected fun bindData(list: List<MovieTv>) {
        rvAdapter.setList(list)
    }

    override fun onItemClicked(data: MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_MOVIE_TV_DATA, data)
            }
        )
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

    override fun onDestroyView() {
        binding?.rvList?.adapter = null
        _binding = null
        super.onDestroyView()
    }

    abstract fun observeViewModel()
    abstract fun requestData()
    abstract fun search(query: String?)
}