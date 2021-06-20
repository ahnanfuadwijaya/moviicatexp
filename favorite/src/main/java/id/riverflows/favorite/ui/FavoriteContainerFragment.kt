package id.riverflows.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.favorite.databinding.FragmentFavoriteContainerBinding
import id.riverflows.favorite.di.favoriteViewModelModule
import id.riverflows.favorite.ui.movie.FavoriteMovieFragment
import id.riverflows.favorite.ui.tv.FavoriteTvFragment
import id.riverflows.moviicatexp.home.HomeActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.context.loadKoinModules
import id.riverflows.favorite.R as favoriteR
import id.riverflows.moviicatexp.R as rootR

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteContainerFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentFavoriteContainerBinding? = null
    private val binding
        get() = _binding
    private var pagerMediator: TabLayoutMediator? = null
    private val movieFragment = FavoriteMovieFragment()
    private val tvFragment = FavoriteTvFragment()
    private val searchView: SearchView by lazy {
        (activity as HomeActivity).findViewById(rootR.id.search_view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteContainerBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadKoinModules(favoriteViewModelModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        val fragmentList = listOf<Fragment>(
            movieFragment, tvFragment
        )
        binding?.run {
            val pagerAdapter = FavoriteSectionsPagerAdapter(
                childFragmentManager,
                viewLifecycleOwner.lifecycle,
                fragmentList
            )
            viewPager.adapter = pagerAdapter
            viewPager.registerOnPageChangeCallback(pageChangeListener)
            pagerMediator = TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(favoriteR.array.movie_tv_tab_titles)
                tab.text = titles[position]
            }
            pagerMediator?.attach()
        }

        searchView.setOnQueryTextListener(this)
        val clearSearchResultButton: ImageView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        clearSearchResultButton.setOnClickListener {
            clearSearchView()
        }
    }

    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            onPageChange()
            super.onPageSelected(position)
        }
    }

    internal fun onPageChange(){
        searchView.setQuery("", false)
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerMediator?.detach()
        pagerMediator = null
        binding?.viewPager?.unregisterOnPageChangeCallback(pageChangeListener)
        binding?.viewPager?.adapter = null
        _binding = null
    }

    private fun search(query: String?){
        query?.run {
            if(binding?.viewPager?.currentItem == 0){
                movieFragment.search(this)
            }else{
                tvFragment.search(this)
            }
        }
    }

    private fun requestData(){
        if(binding?.viewPager?.currentItem == 0){
            movieFragment.requestData()
        }else{
            tvFragment.requestData()
        }
    }

    private fun clearSearchView(){
        if(searchView.query.isEmpty()) {
            searchView.isIconified = true
        } else {
            searchView.setQuery("", false)
        }
        requestData()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.run {
            if(this.isNotBlank()) search(this) else requestData()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.run {
            if(this.isNotBlank()) search(this) else requestData()
        }
        return true
    }
}