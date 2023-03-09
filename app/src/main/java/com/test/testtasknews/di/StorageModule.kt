package com.test.testtasknews.di

import androidx.room.Room
import com.test.testtasknews.constant.Storage
import com.test.testtasknews.data.room.Database
import org.koin.dsl.module

val storageModule = module {

    single {
        Room.databaseBuilder(get(), Database::class.java, Storage.DATABASE_NAME).build()
    }

    single {
        get<Database>().articleDao()
    }

}

