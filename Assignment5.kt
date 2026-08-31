package com.pes.lib

fun getNumber(data: String): Int {
    return try {
        data.toInt()
    } catch (e: NumberFormatException) {
        println("Invalid number: $data")
        0
    }
}

fun main() {
    println(getNumber("12"))
    println(getNumber("a1as"))
}