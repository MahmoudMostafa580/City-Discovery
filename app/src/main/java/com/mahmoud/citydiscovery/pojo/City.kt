package com.mahmoud.citydiscovery.pojo

data class City(val country: String, val name: String, val _id: Double, val coord: Coordinator) {}

data class Coordinator(val lon: Double, val lat: Double) {}
