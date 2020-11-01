package com.compprogserver.controller

import com.compprogserver.controller.request.GetSubmissionsFromHandleRequest
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

    @PostMapping("/get-handle-submissions")
    fun getAllHandleSubmissions(@RequestBody request: GetSubmissionsFromHandleRequest):
            ResponseEntity<List<Submission>> {
        return try {
            val submissions = submissionService.getAllSubmissionsFromUserHandle(request.userHandle, request.platform)
            ResponseEntity(submissions, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/fetch-remote-submissions")
    fun fetchRemoteSubmissionsFromPlatform(@RequestBody request: GetSubmissionsFromHandleRequest):
            ResponseEntity<Collection<Submission>> {
        return try {
            val submissions = submissionService.fetchRemoteSubmissionsFromPlatform(username = request.username,
                    userHandleName = request.userHandle, platformName = request.platform)
            ResponseEntity(submissions, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}