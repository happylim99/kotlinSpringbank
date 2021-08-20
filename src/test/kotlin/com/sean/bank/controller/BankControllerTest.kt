package com.sean.bank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sean.bank.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objMapper: ObjectMapper
) {

    private val baseUrl = "/bank"

    @Nested
    @DisplayName("Delete bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank() {
        @Test
        @DirtiesContext // regenerate spring context after this test, but decrease performance
        fun `should delete bank`() {
            val accNo = "1234"

            mockMvc.delete("$baseUrl/delete/$accNo")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
        }
    }

    @Nested
    @DisplayName("showAll()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class showAll {
        @Test
        fun `should return all banks`() {
            // $ = root
            mockMvc.get("$baseUrl/showAll")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") {
                        value("1234")
                    }
                    jsonPath("$[1].accountNumber") {
                        value("abcd")
                    }
                }
        }
    }

    @Nested
    @DisplayName("showOne()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class showOne {
        @Test
        fun `should find accountNumber 1234`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMvc.get("$baseUrl/showOne/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") {
                        value(0.0)
                    }
                    jsonPath("$.transactionFee") {
                        value(1)
                    }
                }
        }

        @Test
        fun `should return Not Found if account not exist`() {
            val accountNumber = "doest not exist"

            mockMvc.get("$baseUrl/showOne/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
    
    @Nested
    @DisplayName("saveBank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class saveBank() {

        private fun performSave(bank: Bank): ResultActionsDsl {
            return mockMvc.post("$baseUrl/save") {
                contentType = MediaType.APPLICATION_JSON
                content = objMapper.writeValueAsString(bank)
            }
        }

        @Test
        fun `should saveBank`() {
            val newBank = Bank("abc123", 2.2, 2)
            val performSave = performSave(newBank)
                performSave
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") {
                        value("abc123")
                    }
                    jsonPath("$.trust") {
                        value(2.2)
                    }
                    jsonPath("$.transactionFee") {
                        value(2)
                    }
                }
        }
        
        @Test
        fun `should return BAD REQUEST if account number already exist`() {
            val invalidbank = Bank("1234", 0.0,1)
            val performSave = performSave(invalidbank)
            performSave
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }

        }
    }
    
    @Nested
    @DisplayName("Patch bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchBank() {

        private fun performPatch(bank: Bank): ResultActionsDsl {
            return mockMvc.patch("$baseUrl/update") {
                contentType = MediaType.APPLICATION_JSON
                content = objMapper.writeValueAsString(bank)
            }
        }
        @Test
        fun `should update existing bank`() {
            val uptBank = Bank("1234", 2.8, 28)

            performPatch(uptBank)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objMapper.writeValueAsString(uptBank))
                    }
                }

            mockMvc.get("$baseUrl/showOne/${uptBank.accountNumber}")
                .andExpect {
                    content { json(objMapper.writeValueAsString(uptBank)) }
                }
        }
        
        @Test
        fun `should return BAD REQUEST if find nothing`() {
            val invalidbank = Bank("1111", 0.0,1)
            performPatch(invalidbank)
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }
    }
    


}