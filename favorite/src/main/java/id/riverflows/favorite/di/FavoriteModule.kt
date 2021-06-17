package id.riverflows.favorite.di

import id.riverflows.favorite.ui.FavoriteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
val favoriteViewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}