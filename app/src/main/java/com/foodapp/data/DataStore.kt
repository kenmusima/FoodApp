package com.foodapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStore @Inject constructor(context: Context) {

    private val Context.createDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")
    private val imageUri = stringPreferencesKey("PROFILE_URI")
    private val dataStore = context.createDataStore

    suspend fun saveImageProfile(uri: String) {
        dataStore.edit { settings ->
            settings[imageUri] = uri
        }
    }

    val getImageUri: Flow<String?> = dataStore.data.map { uri ->
        uri[imageUri]
    }
}