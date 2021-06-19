package id.riverflows.favorite.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.riverflows.favorite.ui.content.FavoriteMovieFragment
import id.riverflows.favorite.ui.content.FavoriteTvFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class FavoriteSectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val movieFragment = FavoriteMovieFragment()
    private val tvFragment = FavoriteTvFragment()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = if(position==0) movieFragment else tvFragment
}