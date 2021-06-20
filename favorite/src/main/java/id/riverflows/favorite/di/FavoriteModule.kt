package id.riverflows.favorite.di

import id.riverflows.favorite.ui.movie.FavoriteMovieViewModel
import id.riverflows.favorite.ui.tv.FavoriteTvViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val favoriteViewModelModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvViewModel(get()) }
}