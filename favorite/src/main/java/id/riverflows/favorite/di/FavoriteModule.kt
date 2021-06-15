package id.riverflows.favorite.di

import id.riverflows.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}