package com.compprogserver.controller

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.service.CodeforcesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/codeforces")
class CodeforcesController @Autowired constructor(
        private val codeforcesService: CodeforcesService
) {

    @GetMapping("/handle")
    fun getHandle(@RequestParam("username") username: String): ResponseEntity<UserHandle> {
        val handle = codeforcesService.getUserHandle(username)
        return ResponseEntity(handle, HttpStatus.OK)
    }

    @GetMapping("/submissions")
    fun getUserSubmissions(@RequestParam("username") username: String): ResponseEntity<Set<Submission>> {
        val submissions = codeforcesService.getUserSubmissions(username)
        return ResponseEntity(submissions, HttpStatus.OK)
    }
}