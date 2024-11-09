package com.gabrielbotao.swechallenge.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gabrielbotao.swechallenge.data.local.DATA_STORE_FILE_NAME
import com.gabrielbotao.swechallenge.data.local.createDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single { provideDataStore(get()) }
}

private fun provideDataStore(context: Context): DataStore<Preferences> =
    createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }