package id.riverflows.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.domain.model.Content
import id.riverflows.core.ui.adapter.GridRvAdapter
import id.riverflows.core.ui.decoration.SpaceItemDecoration
import id.riverflows.core.utils.AppConfig
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        lifecycleScope.launch(Dispatchers.Main){
            if(type == Content.TYPE_MOVIE){
                viewModel.favoriteMovies.collectLatest{
                    bindData(it)
                }
            }else{
                viewModel.favoriteTvShows.collectLatest {
                    bindData(it)
                }
            }
        }
    }

    private fun bindData(list: List<Content.MovieTv>){
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

    override fun onItemClicked(data: Content.MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_TV_DATA, data)
            }
        )
    }
}