package com.sean.bank.datasource.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.sean.bank.datasource.BankRepo
import com.sean.bank.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository("BankRepoImpl")
class BankRepoImpl: BankRepo {

    val bankList = mutableListOf(
        Bank(
            "1234",
            0.0,
            1
        ),
        Bank(
            "abcd",
            3.0,
            2
        )
    )

    override fun getBankList(): Collection<Bank> = bankList

    override fun showOne(accountNumber: String): Bank {
        val obj = ObjectMapper()
        return bankList.firstOrNull {
            it.accountNumber == accountNumber
        } ?: throw NoSuchElementException(obj.writeValueAsString(Bank(
            "qqqq", 3.2, 3)))
//        bankList.first {
//            it.accountNumber == accountNumber
//        }
    }

    override fun save(bank: Bank): Bank {
        if(bankList.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Account number ${bank.accountNumber} already exist")
        }
        bankList.add(bank)
        return bank
    }

    override fun update(bank: Bank): Bank {
//        val currentBank = bankList.firstOrNull { it.accountNumber == bank.accountNumber }
//                ?: throw NoSuchElementException("Account number ${bank.accountNumber} not found")
        val currentBank = findByAccountNumber(bank.accountNumber)
        bankList.remove(currentBank)
        bankList.add(bank)
        return bank
    }

    override fun delete(accNo: String): Unit {
        val currentBank = findByAccountNumber(accNo)
        bankList.remove(currentBank)
    }

    private fun findByAccountNumber(accNo: String): Bank {
        return bankList.firstOrNull { it.accountNumber == accNo }
            ?: throw NoSuchElementException("Account number ${accNo} not found")
    }
}