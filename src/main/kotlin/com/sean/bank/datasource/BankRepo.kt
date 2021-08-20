package com.sean.bank.datasource

import com.sean.bank.model.Bank

interface BankRepo {

    fun getBankList(): Collection<Bank>
    fun showOne(accountNumber: String): Bank
    fun save(bank: Bank): Bank
    fun update(bank: Bank): Bank
    fun delete(accNo: String): Unit
}