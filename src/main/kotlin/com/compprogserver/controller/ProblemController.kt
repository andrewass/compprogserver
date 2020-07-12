package com.compprogserver.controller

import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.entity.problem.Problem
import com.compprogserver.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin("*")
@RequestMapping("/problem")
class ProblemController @Autowired constructor(
        private val problemService: ProblemService
){
    @GetMapping("/trending-problems")
    fun getProblems() : ResponseEntity<List<Problem>> {
        return ResponseEntity(problemService.getPopularProblems(), HttpStatus.OK)
    }

    @PostMapping("/add-problem")
    fun addProblem(@RequestBody request : AddProblemRequest) : HttpStatus{
        val problem = problemService.addProblem(request)
        return HttpStatus.OK
    }
}