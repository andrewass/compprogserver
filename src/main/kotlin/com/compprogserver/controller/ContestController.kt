package com.compprogserver.controller

import com.compprogserver.entity.Contest
import com.compprogserver.service.ContestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/contest")
class ContestController @Autowired constructor(
        private val contestService: ContestService
) {

    @GetMapping("/upcoming-contests")
    fun getUpcomingContests(): List<Contest> {
        return contestService.getUpcomingContests()
    }
}