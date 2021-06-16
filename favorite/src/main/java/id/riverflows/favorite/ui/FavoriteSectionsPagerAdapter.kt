package id.riverflows.favorite.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.riverflows.core.domain.model.Content

class FavoriteSectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val movieFragment = FavoriteContentFragment.newInstance(Content.TYPE_MOVIE)
    private val tvFragment = FavoriteContentFragment.newInstance(Content.TYPE_TV)
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = if(position==0) movieFragment  else tvFragment
}