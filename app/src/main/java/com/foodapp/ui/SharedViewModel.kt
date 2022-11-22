package com.foodapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.data.repository.Repository
import com.foodapp.data.db.entity.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun fetchFoodCategory(query: String): Flow<List<Food>> = repository.getFoodByCategory(query)

    fun fetchFavourite(): LiveData<List<Food>> = repository.getFavouriteFoods()
    fun deleteFavourite(id: Int) = viewModelScope.launch(Dispatchers.IO) { repository.deleteFavouriteById(id) }
}