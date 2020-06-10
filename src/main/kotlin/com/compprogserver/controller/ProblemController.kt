package com.compprogserver.controller

import com.compprogserver.entity.problem.Problem
import com.compprogserver.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin("*")
@RequestMapping("/problem")
class ProblemController @Autowired constructor(
        private val problemService: ProblemService
){
    @GetMapping("/trending-problems")
    fun getProblems() : ResponseEntity<Collection<Problem>> {
        return ResponseEntity(problemService.getTrendingProblems(), HttpStatus.OK)
    }
}