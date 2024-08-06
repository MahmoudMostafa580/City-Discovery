package com.mahmoud.citydiscovery.pojo

//Implement Comparable interface to sort the list of cities in alphabetical order.
data class City(val country: String, val name: String, val _id: Double, val coord: Coordinator) :
    Comparable<City> {
    override fun compareTo(other: City): Int {
        return this.name.compareTo(other.name)
    }
}

data class Coordinator(val lon: Double, val lat: Double) {}
