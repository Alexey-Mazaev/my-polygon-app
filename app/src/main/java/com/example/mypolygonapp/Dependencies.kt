package com.example.mypolygonapp

import com.example.mypolygonapp.data.reps.StocksRepository
import com.example.mypolygonapp.data.reps.StocksRepositoryImpl
import com.example.mypolygonapp.ui.MainViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}

val repositoryModule = module {
    fun provideStocksRepository(okHttpClient: OkHttpClient): StocksRepository =
        StocksRepositoryImpl(okHttpClient)

    single { provideStocksRepository(get()) }
}

val okHttpClientModule = module {
    fun provideOkHttpClient() = OkHttpClient.Builder().build()

    single { provideOkHttpClient() }
}