package com.pes.lib

// Functional programming, collections
fun main() {

    val phoneNumbers = listOf<String>(
        "1234567890",
        "123",
        "",
        "0987654321"
    )

    // 1. Using lambda, filter valid phone numbers - 10 digits
    val validNumbers = phoneNumbers.filter { it.length == 10 }

    println("Valid phone numbers:")
    validNumbers.forEach {
        println(it)
    }


    // 2. Using lambda, print all phone numbers,
    // if invalid - print 'NAN'
    val result = phoneNumbers.map {
        if (it.length == 10) it else "NAN"
    }

    println("\nAll phone numbers:")
    result.forEach {
        println(it)
    }
}