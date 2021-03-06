package id.riverflows.moviicatexp.di

import id.riverflows.core.domain.usecase.MovieTvInteractor
import id.riverflows.core.domain.usecase.MovieTvUseCase
import id.riverflows.moviicatexp.detail.DetailViewModel
import id.riverflows.moviicatexp.home.movie.HomeMovieViewModel
import id.riverflows.moviicatexp.home.tv.HomeTvViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieTvUseCase> { MovieTvInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val appViewModelModule = module {
    viewModel { HomeMovieViewModel(get()) }
    viewModel { HomeTvViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}