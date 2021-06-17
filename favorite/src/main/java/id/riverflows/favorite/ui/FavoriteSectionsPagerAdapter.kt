package id.riverflows.favorite.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.riverflows.core.utils.UtilConstants.TYPE_MOVIE
import id.riverflows.core.utils.UtilConstants.TYPE_TV
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class FavoriteSectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val movieFragment: Fragment
    private val tvFragment: Fragment

    init {
        movieFragment = FavoriteContentFragment.newInstance(TYPE_MOVIE)
        tvFragment = FavoriteContentFragment.newInstance(TYPE_TV)
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = if(position==0) {
        movieFragment
    }  else {
        tvFragment
    }
}