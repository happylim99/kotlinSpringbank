package com.sean.bank.datasource.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.sean.bank.datasource.BankRepo
import com.sean.bank.datasource.network.dto.Restaurant
import com.sean.bank.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException
import java.lang.IllegalArgumentException

@Repository("BankRepoNetworkImpl")
class BankRepoNetworkImpl @Autowired constructor(
    private val restTemplate: RestTemplate
): BankRepo {
    private val url = "https://random-data-api.com/api/restaurant/random_restaurant?size=5"
    fun getRestaurantList(): Collection<Restaurant> {
        val response: ResponseEntity<Collection<Restaurant>> =
            restTemplate.getForEntity<Collection<Restaurant>>(url)
        println(response.body)
        return response.body ?: throw IOException("hahaha")
    }

    override fun getBankList(): Collection<Bank> {
        TODO()
    }

    override fun showOne(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun save(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun update(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun delete(accNo: String) {
        TODO("Not yet implemented")
    }


}