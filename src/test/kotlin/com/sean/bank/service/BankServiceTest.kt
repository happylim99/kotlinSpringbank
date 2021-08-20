package com.sean.bank.service

import com.sean.bank.datasource.BankRepo
import com.sean.bank.datasource.impl.BankRepoImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val bankRepo: BankRepo = mockk(relaxed = true)
    private val bankService = BankService(bankRepo)

    @Test
    fun `should call its data source to retrieve banks`() {
        //given
        // same like relaxed true
//        every { bankRepo.getBankList() } returns emptyList()

        // when
        val bankList = bankService.getBankList()

        // then
        verify(exactly = 1) { bankRepo.getBankList() }
    }
}