package com.mahmoud.citydiscovery.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.citydiscovery.databinding.ActivityMainBinding
import com.mahmoud.citydiscovery.pojo.City
import com.mahmoud.citydiscovery.utils.Trie
import com.mahmoud.citydiscovery.viewmodel.CityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CityAdapter
    lateinit var binding: ActivityMainBinding
    val trie = Trie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Bind layout views
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        //Initialize viewmodel
        val cityViewModel: CityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        //Handle click on city item to open google maps.
        adapter = CityAdapter(object : CityAdapter.OnItemClickListener {
            override fun onItemClick(city: City) {
                cityViewModel.navigateToMaps(city)
            }
        })

        //Observe on cities properties
        cityViewModel.citiesProperties.observe(this) {
            if (it != null) {
                binding.citiesList.adapter = adapter
                adapter.submitList(it)

                Log.i("data completed: ", it.size.toString())

                searchCities(adapter, it)
            }
        }

        //Navigate to google maps
        cityViewModel.navigateToGoogleMaps.observe(this) {
            if (it != null) {
                openGoogleMaps(it.coord.lon, it.coord.lat)
            }
        }

    }

    private fun searchCities(adapter: CityAdapter, citiesList: List<City>?) {
        // Insert cities into the Trie for searching process
//        for (city in citiesList!!) {
//            trie.insert(city)
//        }

        // SearchView listener implementation
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.trim().isNotEmpty()) {
//                    val filteredList = trie.search(query)
                    val filteredList = citiesList?.filter { it.name.startsWith(query) }
                    binding.searchView.clearFocus()
                    adapter.filterList(filteredList!!)
                    Log.i("Submit Filtered List:", "${filteredList.size}")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.trim().isNotEmpty()) {
//                    val filteredList = trie.search(newText)
                    val filteredList = citiesList?.filter { it.name.startsWith(newText) }
                    adapter.filterList(filteredList!!)
                    Log.i("OnChange Filtered List:", "${filteredList.size}")
                }else{
                    adapter.filterList(citiesList!!)
                }
                return false
            }
        })

        //Listener on close search view
        binding.searchView.setOnCloseListener {
            binding.searchView.setQuery("", false)
            binding.searchView.clearFocus()
            adapter.submitList(null)
            adapter.submitList(citiesList)
            adapter.notifyDataSetChanged()
            true

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