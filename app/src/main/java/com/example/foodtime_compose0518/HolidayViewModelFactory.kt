package com.example.foodtime_compose0518

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HolidayViewModelFactory(private val dao: FoodDao,private val holidayDetailDao: HolidayDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HolidayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HolidayViewModel(dao, holidayDetailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

