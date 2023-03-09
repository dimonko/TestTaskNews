package com.test.testtasknews

import android.app.Application
import com.test.testtasknews.di.apiModule
import com.test.testtasknews.di.networkModule
import com.test.testtasknews.di.storageModule
import com.test.testtasknews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TestTaskNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TestTaskNewsApplication)
            modules(listOf(apiModule, viewModelModule, networkModule, storageModule))
        }
    }

}