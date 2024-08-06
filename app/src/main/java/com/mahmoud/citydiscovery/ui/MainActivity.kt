package com.mahmoud.citydiscovery.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.databinding.ActivityMainBinding
import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        cityViewModel.citiesProperties.observe(this) {
            if (it != null) {
                val adapter = CityAdapter(object : CityAdapter.OnItemClickListener {
                    override fun onItemClick(lon: Double, lat: Double) {
                        openGoogleMaps(lon, lat)
                    }
                })
                adapter.submitList(it)
                binding.citiesList.adapter = adapter
                Log.i("data completed: ", it.size.toString())
            }
        }
    }

    private fun openGoogleMaps(lon: Double, lat: Double) {
        val mapsUri = Uri.parse("geo:$lat,$lon")

        val mapIntent = Intent(Intent.ACTION_VIEW,mapsUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager)!=null){
            startActivity(mapIntent)
        }else{
            Toast.makeText(application, "Error While opening google maps!", Toast.LENGTH_SHORT).show()
        }
    }


}