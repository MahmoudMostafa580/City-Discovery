package com.mahmoud.citydiscovery.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.R

import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        cityViewModel.citiesProperties.observe(this, Observer{
            if (it !=null){
                Log.i("DATA SIZE:: ", it.size.toString())
            }
        })
    }


}