package com.mahmoud.citydiscovery.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoud.citydiscovery.MyApplication
import com.mahmoud.citydiscovery.pojo.City

class CityViewModel(application: Application) : AndroidViewModel(application) {

    val application: MyApplication = getApplication()

    private var _citiesProperties = MutableLiveData<List<City>>()
    val citiesProperties: LiveData<List<City>>
        get() = _citiesProperties

init {
    getCitiesData()
}

    private fun getCitiesData(){
        _citiesProperties.value = application.getCities().sorted()
    }
}