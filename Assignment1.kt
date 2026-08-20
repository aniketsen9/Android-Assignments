package com.pes.lib

fun main() {

    val data = "Hi! How are you?"

    // 1. How many vowels in the string
    var vowels = 0

    for (ch in data) {
        when (ch.lowercaseChar()) {
            'a', 'e', 'i', 'o', 'u' -> vowels++
        }
    }

    println("Number of vowels: $vowels")


    // 2. How many spaces
    var spaces = 0

    for (ch in data) {
        when (ch) {
            ' ' -> spaces++
        }
    }

    println("Number of spaces: $spaces")
}