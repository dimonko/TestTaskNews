package com.test.testtasknews.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.testtasknews.constant.Api
import com.test.testtasknews.data.api.ApiInterceptor
import com.test.testtasknews.data.api.service.NewsApiService
import com.test.testtasknews.data.repository.NewsRepository
import com.test.testtasknews.data.repository.NewsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    single<OkHttpClient.Builder> {
        OkHttpClient.Builder().connectTimeout(Api.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Api.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Api.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    }

    single {
        ApiInterceptor(internetConnectionChecker = get())
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    fun provideNewsApiService(
        okHttpClient: OkHttpClient.Builder, interceptor: ApiInterceptor
    ): NewsApiService {
        okHttpClient.addInterceptor(provideLoggingInterceptor())
        okHttpClient.addInterceptor(interceptor)
        return provideRetrofitBuilder().client(okHttpClient.build()).build()
            .create(NewsApiService::class.java)
    }

    single<NewsRepository> {
        NewsRepositoryImpl(
            provideNewsApiService(
                okHttpClient = get(), interceptor = get()
            )
        )
    }

}