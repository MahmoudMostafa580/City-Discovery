package com.mahmoud.citydiscovery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.citydiscovery.databinding.CityLayoutBinding
import com.mahmoud.citydiscovery.pojo.City

class CityAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<City, CityAdapter.CityViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }

    class CityViewHolder(private var binding: CityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.cityName.text = "${city.name} ,"
            binding.cityCountry?.text = "${city.country}"
            binding.cityCoordinatesLon.text = "${city.coord.lon} ,"
            binding.cityCoordinatesLat.text = " ${city.coord.lat}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(CityLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(city)
        }
    }

    fun filterList(citiesList:List<City>){
        submitList(null)
        submitList(citiesList)
        notifyDataSetChanged()
    }

    //Interface to handle click listener on items of list.
    interface OnItemClickListener {
        fun onItemClick(city: City)
    }
}