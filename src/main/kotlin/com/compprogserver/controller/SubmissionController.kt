package com.compprogserver.controller

import com.compprogserver.controller.request.GetAllSubmissionFromHandleRequest
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

    @GetMapping("/get-handle-submissions")
    fun getAllHandleSubmissions(@RequestBody request: GetAllSubmissionFromHandleRequest):
            ResponseEntity<List<Submission>> {
        return try {
            val submissions = submissionService.getAllSubmissionsFromHandle(request.userHandle, request.platform)
            ResponseEntity(submissions, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("/fetch-remote-submissions")
    fun fetchRemoteSubmissionsFromPlatform(@RequestBody request: GetAllSubmissionFromHandleRequest):
            ResponseEntity<Collection<Submission>> {
        return try {
            val submissions = submissionService.fetchRemoteSubmissionsFromPlatform(request.userHandle, request.platform)
            ResponseEntity(submissions, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}