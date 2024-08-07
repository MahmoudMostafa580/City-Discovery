package com.mahmoud.citydiscovery.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoud.citydiscovery.MyApplication
import com.mahmoud.citydiscovery.pojo.City

class CityViewModel(application: Application) : AndroidViewModel(application) {

    val application: MyApplication = getApplication()

    private var _citiesProperties = MutableLiveData<List<City>>()
    val citiesProperties: LiveData<List<City>>
        get() = _citiesProperties

    private var _navigateToGoogleMaps = MutableLiveData<City>()
    val navigateToGoogleMaps: LiveData<City>
        get() = _navigateToGoogleMaps

    init {
        getCitiesData()
    }

    private fun getCitiesData() {
        _citiesProperties.value = application.getCities().sorted()
    }

    fun navigateToMaps(city: City) {
        _navigateToGoogleMaps.value = city
    }
}