package com.pes.lib

fun convert(names: Array<String?>) {

    for (name in names) {

        // 1. Explicit null check
        if (name != null) {
            println("Explicit check: ${name.uppercase()}")
        }

        // 2. Safe call operator
        println("Safe call: ${name?.uppercase()}")

        // 3. Elvis operator
        val result = name?.uppercase() ?: "UNKNOWN"
        println("Elvis operator: $result")

        // 4. Assertion operator
        if (name != null) {
            println("Assertion operator: ${name!!.uppercase()}")
        }

        println()
    }
}

fun main() {

    convert(arrayOf("john", null, "merry", "robert"))
}