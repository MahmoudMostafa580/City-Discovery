package com.mahmoud.citydiscovery.utils

import com.mahmoud.citydiscovery.pojo.City

class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    var isEndOfWord: Boolean = false
    var city: City? = null
}