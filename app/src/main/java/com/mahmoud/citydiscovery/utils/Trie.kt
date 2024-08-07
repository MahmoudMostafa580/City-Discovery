package com.mahmoud.citydiscovery.utils

import com.mahmoud.citydiscovery.pojo.City

class Trie {
    private val root = TrieNode()

    // Insert a city into the trie
    fun insert(city: City) {
        var node = root
        for (char in city.name) {
            node = node.children.getOrPut(char) { TrieNode() }
        }
        node.isEndOfWord = true
        node.city = city
    }

    // Search for all cities that start with the given prefix
    fun search(prefix: String): List<City> {
        var node = root
        for (char in prefix) {
            node = node.children[char] ?: return emptyList()
        }
        return collectAllCities(node)
    }

    // Collect all cities starting from the given node
    private fun collectAllCities(node: TrieNode): List<City> {
        val result = mutableListOf<City>()
        if (node.isEndOfWord && node.city != null) {
            result.add(node.city!!)
        }
        for ((_, childNode) in node.children) {
            result.addAll(collectAllCities(childNode))
        }
        return result
    }

}