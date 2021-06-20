package id.riverflows.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.utils.AppConfig
import id.riverflows.core.utils.State
import id.riverflows.core.utils.State.*
import id.riverflows.core.utils.UtilConstants
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.ui.GridRvAdapter
import id.riverflows.moviicatexp.ui.SpaceItemDecoration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
abstract class FavoriteFragment: Fragment(), GridRvAdapter.OnItemClickCallback {
    private var _binding: FragmentListContainerBinding? = null
    private val binding
        get() = _binding
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
    }

    override fun onResume() {
        super.onResume()
        requestData()
    }

    protected fun setState(state: State){
        when (state) {
            SUCCESS -> {
                binding?.rvList?.visibility = View.VISIBLE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            LOADING -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.startShimmer()
                binding?.viewLoadingShimmer?.visibility = View.VISIBLE
                binding?.viewNoData?.root?.visibility = View.GONE
            }
            NO_DATA -> {
                binding?.rvList?.visibility = View.GONE
                binding?.viewLoadingShimmer?.stopShimmer()
                binding?.viewLoadingShimmer?.visibility = View.GONE
                binding?.viewNoData?.root?.visibility = View.VISIBLE
            }
            else -> return
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