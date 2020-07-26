package com.compprogserver.controller

import com.compprogserver.controller.request.SubmissionRequest
import com.compprogserver.entity.problem.Submission
import com.compprogserver.service.SubmissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/submission")
class SubmissionController @Autowired constructor(
        val submissionService: SubmissionService
) {

    @GetMapping("/submissions")
    fun getAllSubmissions(@RequestBody submissionRequest: SubmissionRequest): ResponseEntity<Collection<Submission>> {
        val submissions = submissionService.getSubmissions(submissionRequest)

        return ResponseEntity(submissions, HttpStatus.OK)
    }
}