package com.gabrielbotao.swechallenge.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(producePatch: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePatch().toPath() }
    )
}

internal const val DATA_STORE_FILE_NAME = "settings.preferences_pb"