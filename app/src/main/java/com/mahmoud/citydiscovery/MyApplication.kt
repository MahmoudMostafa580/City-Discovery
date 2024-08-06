package com.mahmoud.citydiscovery

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoud.citydiscovery.pojo.City

class MyApplication : Application() {

    private lateinit var cities: List<City>
    override fun onCreate() {
        super.onCreate()

        val jsonFileString = getJsonDataFromAsset(applicationContext, "cities.json")
        Log.i("data", jsonFileString!!)

        /*
        Use Gson as Json parser because Gson is known for its simple and easy to use,
        performance and type safety.
         */


        val gson = Gson()
        val listCityType = object : TypeToken<List<City>>() {}.type

        cities = gson.fromJson(jsonFileString, listCityType)
    }

    fun getCities(): List<City> {
        return cities
    }
}