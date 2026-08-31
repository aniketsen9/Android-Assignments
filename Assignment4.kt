package com.pes.lib

open class BankAccount(
    val accName: String,
    val accNumber: Int,
    var balance: Int = 0
) {

    open fun displayInfo() {
        println("Account Holder Name: $accName")
        println("Account Number: $accNumber")
        println("Balance: $balance")
    }
}

class SavingAccount(
    accName: String,
    accNumber: Int,
    balance: Int = 0,
    val interestRate: Float
) : BankAccount(accName, accNumber, balance) {

    fun credit(amount: Int) {
        balance += amount
    }

    fun debit(amount: Int) {
        balance -= amount
    }

    override fun displayInfo() {
        super.displayInfo()
        println("Interest Rate: $interestRate")
    }
}

fun main() {

    val account = SavingAccount(
        accName = "Aniket Sen",
        accNumber = 12345,
        interestRate = 5.5f
    )

    account.credit(10000)
    account.debit(2000)

    account.displayInfo()
}