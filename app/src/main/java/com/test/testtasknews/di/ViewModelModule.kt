package com.test.testtasknews.di

import com.test.testtasknews.viewmodel.FavouritesActivityViewModel
import com.test.testtasknews.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainActivityViewModel(newsRepository = get(), articleDao = get())
    }

    viewModel {
        FavouritesActivityViewModel(articleDao = get())
    }
}