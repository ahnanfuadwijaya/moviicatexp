package id.riverflows.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.presentation.ui.adapter.GridRvAdapter
import id.riverflows.core.presentation.ui.decoration.SpaceItemDecoration
import id.riverflows.core.utils.AppConfig
import id.riverflows.core.utils.State
import id.riverflows.core.utils.State.NO_DATA
import id.riverflows.core.utils.State.SUCCESS
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


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
    }

    private fun observeViewModel(){
        if(type == TYPE_MOVIE){
            viewModel.favoriteMovies.observe(viewLifecycleOwner){
                if(it.isEmpty()) {
                    setState(NO_DATA)
                } else {
                    setState(SUCCESS)
                    bindData(it)
                }
                Timber.d("$type, $it")
            }
        }else{
            viewModel.favoriteTvShows.observe(viewLifecycleOwner){
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

    companion object {
        @JvmStatic
        fun newInstance(type: Int) = FavoriteContentFragment(type)
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
}