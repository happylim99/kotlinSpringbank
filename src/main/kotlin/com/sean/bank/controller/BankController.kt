package com.sean.bank.controller

import com.sean.bank.datasource.network.dto.Restaurant
import com.sean.bank.model.Bank
import com.sean.bank.service.BankService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.NoSuchElementException

@RestController
@RequestMapping("/bank")
class BankController(
    private val bankSrv: BankService
) {

//    @Autowired
//    private lateinit var bankSrv: BankService

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping("/showAll")
    fun showAll(): Collection<Bank> = bankSrv.getBankList()

    @GetMapping("/showOne/{accountNumber}")
    fun showOne(@PathVariable accountNumber: String) =
        bankSrv.showOne(accountNumber)

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody bank: Bank): Bank =
        bankSrv.save(bank)

    @PatchMapping("/update")
//    @ResponseStatus(HttpStatus.CREATED)
    fun update(@RequestBody bank: Bank): Bank =
        bankSrv.update(bank)

    @DeleteMapping("/delete/{accNo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable accNo: String): Unit =
        bankSrv.delete(accNo)

    @GetMapping("/restaurant")
    fun restaurant(): Collection<Restaurant> = bankSrv.getRestaurantList()
}