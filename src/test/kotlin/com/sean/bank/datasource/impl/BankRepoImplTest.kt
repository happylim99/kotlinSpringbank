package com.sean.bank.datasource.impl


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankRepoImplTest {

    private val bankRepo = BankRepoImpl()

    @Test
    fun `should provide a collection of banks`() {
        // given


        // when
        val bankList = bankRepo.getBankList()


        // then
        assertThat(bankList).isNotEmpty
        assertThat(bankList.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        val bankList = bankRepo.getBankList()
        assertThat(bankList).allMatch {
            it.accountNumber.isNotBlank()
        }
        assertThat(bankList).anyMatch {
            it.trust == 0.0
        }
    }
}