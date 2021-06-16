package id.riverflows.moviicatexp.home.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.core.data.Resource
import id.riverflows.core.domain.model.Content
import id.riverflows.core.ui.adapter.GridRvAdapter
import id.riverflows.core.ui.decoration.SpaceItemDecoration
import id.riverflows.core.utils.AppConfig.GRID_ITEM_COUNT
import id.riverflows.core.utils.AppConfig.SPACE_ITEM_DECORATION
import id.riverflows.core.utils.UtilConstants.EXTRA_MOVIE_TV_DATA
import id.riverflows.moviicatexp.databinding.FragmentListContainerBinding
import id.riverflows.moviicatexp.detail.DetailActivity
import id.riverflows.moviicatexp.home.HomeSharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MovieFragment : Fragment(), GridRvAdapter.OnItemClickCallback {
    private val viewModel: HomeSharedViewModel by viewModel()
    private val rvAdapter = GridRvAdapter()
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
    }

    private fun setupView(){
        rvAdapter.setOnItemClickCallback(this)
        with(binding?.rvList){
            this?.setHasFixedSize(true)
            this?.layoutManager = GridLayoutManager(context, GRID_ITEM_COUNT)
            this?.addItemDecoration(SpaceItemDecoration(SPACE_ITEM_DECORATION))
            this?.adapter = rvAdapter
        }
    }

    private fun observeViewModel(){
        viewModel.movies.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{}
                is Resource.Success -> {
                    it.data?.let { data -> rvAdapter.setList(data) }
                }
                is Resource.Error -> {
                    Timber.d(it.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvList?.adapter = null
        _binding = null
    }

    override fun onItemClicked(data: Content.MovieTv) {
        startActivity(
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_TV_DATA, data)
            }
        )
    }
}