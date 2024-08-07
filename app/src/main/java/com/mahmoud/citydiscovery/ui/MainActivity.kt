package com.mahmoud.citydiscovery.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.databinding.ActivityMainBinding
import com.mahmoud.citydiscovery.pojo.City
import com.mahmoud.citydiscovery.utils.Trie
import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val trie = Trie()

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        cityViewModel.citiesProperties.observe(this) { it ->
            if (it != null) {
                //Handle click on city item to open google maps.
                val adapter = CityAdapter(object : CityAdapter.OnItemClickListener {
                    override fun onItemClick(city: City) {
                        cityViewModel.navigateToMaps(city)
                    }
                })
                adapter.submitList(it)
                binding.citiesList.adapter = adapter
                Log.i("data completed: ", it.size.toString())

                // Insert cities into the Trie
                for (city in it) {
                    trie.insert(city)
                }

                // SearchView listener implementation
                binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query!!.isNotEmpty()){
                            val filteredList = trie.search(query)
                            adapter.filterList(filteredList)
                            Log.i("Submit Filtered List:: ", "${filteredList.size}")
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText!!.isNotEmpty()){
                            val filteredList = trie.search(newText)
                            adapter.filterList(filteredList)
                            Log.i("Filtered List:: ", "${filteredList.size}")
                        }
                        return true
                    }
                })

                //Listener on close search view
                binding.searchView.setOnCloseListener {
                    binding.searchView.clearFocus()
                    binding.searchView.setQuery("",false)
                    adapter.submitList(null)
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                    true

                }
            }
        }

        cityViewModel.navigateToGoogleMaps.observe(this) {
            if (it != null) {
                openGoogleMaps(it.coord.lon, it.coord.lat)
            }
        }


    }

    //open location of city using long and lat of it.
    private fun openGoogleMaps(lon: Double, lat: Double) {
        val mapsUri = Uri.parse("geo:$lat,$lon")

        val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(application, "Error While opening google maps!", Toast.LENGTH_SHORT)
                .show()
        }
    }


}