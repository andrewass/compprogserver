package com.compprogserver.controller

import com.compprogserver.service.CommonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/common")
class CommonController @Autowired constructor(
        private val commonService: CommonService
) {

    @GetMapping("/platforms")
    fun getAllPlatforms(): ResponseEntity<List<String>> {
        val platforms = commonService.getAllPlatforms()
        return ResponseEntity.ok(platforms)
    }
}