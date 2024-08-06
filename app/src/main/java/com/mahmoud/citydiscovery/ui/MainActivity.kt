package com.mahmoud.citydiscovery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.R
import com.mahmoud.citydiscovery.databinding.ActivityMainBinding
import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)



        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        cityViewModel.citiesProperties.observe(this, Observer {
            if (it != null) {
                val adapter = CityAdapter()
                adapter.submitList(it)
                binding.citiesList.adapter = adapter
                Log.i("data completed: ", it.size.toString())
            }
        })
    }


}