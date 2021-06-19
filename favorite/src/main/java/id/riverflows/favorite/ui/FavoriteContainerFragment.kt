package id.riverflows.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.favorite.R
import id.riverflows.favorite.databinding.FragmentFavoriteContainerBinding
import id.riverflows.favorite.di.favoriteViewModelModule
import id.riverflows.favorite.ui.adapter.FavoriteSectionsPagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.context.loadKoinModules

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteContainerFragment : Fragment() {
    private var _binding: FragmentFavoriteContainerBinding? = null
    private val binding
        get() = _binding
    private lateinit var pagerAdapter: FavoriteSectionsPagerAdapter
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
        binding?.run {
            pagerAdapter = FavoriteSectionsPagerAdapter(parentFragmentManager, lifecycle)
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(R.array.movie_tv_tab_titles)
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}