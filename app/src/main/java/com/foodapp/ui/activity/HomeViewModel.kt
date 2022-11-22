package com.foodapp.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.data.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val datastore: DataStore) : ViewModel() {

    fun saveProfileUri(uri: String) = viewModelScope.launch {
        datastore.saveImageProfile(uri)
    }
    val profileUri = datastore.getImageUri
}