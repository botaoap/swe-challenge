package com.gabrielbotao.swechallenge

import android.app.Application
import com.gabrielbotao.swechallenge.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApplication)
        }
    }
}