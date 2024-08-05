package com.mahmoud.citydiscovery

import android.app.Application
import android.util.Log
import com.mahmoud.citydiscovery.pojo.City
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class MyApplication : Application() {

    private lateinit var cities: List<City>
    override fun onCreate() {
        super.onCreate()

        val jsonFileString = getJsonDataFromAsset(applicationContext, "cities.json")
        Log.i("data", jsonFileString!!)

        /*
        Use moshi as Json parser instead of Gson because moshi is known for its flexibility,
        performance, kotlin support, and easy to apply.
         */

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val listAdapter: JsonAdapter<List<City>> = moshi.adapter(type)

        cities = listAdapter.fromJson(jsonFileString)!!
    }

    fun getCities(): List<City> {
        return cities
    }
}