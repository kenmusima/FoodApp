package com.foodapp.utils

import androidx.lifecycle.MutableLiveData
import java.util.*
var currency: Currency = Currency.getInstance(Locale.getDefault())

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}