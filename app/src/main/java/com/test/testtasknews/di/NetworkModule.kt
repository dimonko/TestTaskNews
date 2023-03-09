package com.test.testtasknews.di

import com.test.testtasknews.provider.InternetConnectionChecker
import org.koin.dsl.module

val networkModule = module {

    single {
        InternetConnectionChecker(context = get())
    }

}