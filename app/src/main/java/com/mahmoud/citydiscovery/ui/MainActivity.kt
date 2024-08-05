package com.mahmoud.citydiscovery.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.mahmoud.citydiscovery.MyApplication
import com.mahmoud.citydiscovery.R

import com.mahmoud.citydiscovery.pojo.City

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val cities: List<City>? = (application as? MyApplication)?.getCities()
        Log.i("DATA SIZE:: ", cities?.size.toString())
        cities?.forEachIndexed { idx, city -> Log.i("data", "> Item $idx:\n$city") }
    }


}