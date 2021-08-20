package com.sean.bank.service

import com.sean.bank.datasource.BankRepo
import com.sean.bank.datasource.impl.BankRepoNetworkImpl
import com.sean.bank.datasource.network.dto.Restaurant
import com.sean.bank.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler

@Service
class BankService(
    @Qualifier("BankRepoImpl") private val bankRepo: BankRepo
//    private val restaurantRepo: BankRepoNetworkImpl
) {

    fun getBankList(): Collection<Bank> =
        bankRepo.getBankList()

    fun showOne(accountNumber: String): Bank =
        bankRepo.showOne(accountNumber)

    fun save(bank: Bank): Bank =
        bankRepo.save(bank)

    fun update(bank: Bank): Bank =
        bankRepo.update(bank)

    fun delete(accNo: String) =
        bankRepo.delete(accNo)

//    fun getRestaurantList(): Collection<Restaurant> =
//        restaurantRepo.getRestaurantList()
}