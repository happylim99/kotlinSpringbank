package com.sean.bank.controller

import com.sean.bank.service.SeanService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sean")
class SeanController {

    @Autowired
    private lateinit var seanSrv: SeanService

    @GetMapping("/helloWorld")
    fun helloWorld(): String {
        return seanSrv.helloWorld()
    }
}