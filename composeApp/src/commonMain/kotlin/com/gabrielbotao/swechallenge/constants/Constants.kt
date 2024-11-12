package com.gabrielbotao.swechallenge.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UrlProvider {
    const val BASE_URL = "https://dummyjson.com"
}

object DataStoreKey {
    val USERNAME_KEY = stringPreferencesKey("username")
    val FULL_NAME_KEY = stringPreferencesKey("full_name")
    val EMAIL_KEY = stringPreferencesKey("email")
    val PROFILE_IMAGE_KEY = stringPreferencesKey("profile_image")
    val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
}