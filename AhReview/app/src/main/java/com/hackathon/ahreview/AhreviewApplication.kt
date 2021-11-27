package com.hackathon.ahreview

import android.app.Application
import com.hackathon.ahreview.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AhreviewApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AhreviewApplication)
            val modules = listOf(ViewModelModule,
                RepositoryModule,
                NetworkServiceModule,
                ServiceModule)

            modules(modules)
        }
    }
}