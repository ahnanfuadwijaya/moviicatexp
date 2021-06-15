package id.riverflows.moviicatexp.di

import id.riverflows.core.domain.usecase.MovieTvInteractor
import id.riverflows.core.domain.usecase.MovieTvUseCase
import id.riverflows.moviicatexp.detail.DetailViewModel
import id.riverflows.moviicatexp.home.HomeSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieTvUseCase> { MovieTvInteractor(get()) }
}

val appViewModelModule = module {
    viewModel { HomeSharedViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}