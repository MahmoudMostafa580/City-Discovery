package com.mahmoud.citydiscovery.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.databinding.ActivityMainBinding
import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        cityViewModel.citiesProperties.observe(this) {
            if (it != null) {
                //Handle click on city item to open google maps.
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
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(application, "Asabah", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(application, "Amassy", Toast.LENGTH_SHORT).show()
                return true
            }

        })
    }

    //open location of city using long and lat of it.
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