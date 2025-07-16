package com.eliascoelho911.ebookreader.presentation

import android.app.Application
import com.eliascoelho911.booksdk.BookSdkApplication
import com.eliascoelho911.ebookreader.di.bookSdkModule
import com.eliascoelho911.ebookreader.library.di.libraryModule
import com.eliascoelho911.ebookreader.home.di.homeModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    private val bookSdkApplication: BookSdkApplication by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(bookSdkModule, libraryModule, homeModule)
        }

        bookSdkApplication.init()
    }

    override fun onTerminate() {
        super.onTerminate()

        bookSdkApplication.stop()
    }
}