package com.compprogserver.controller

import com.compprogserver.controller.request.GetAllSubmissionFromHandleRequest
import com.compprogserver.controller.request.GetAllSubmissionFromUserRequest
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
        val submissions = submissionService.getAllSubmissionsFromHandle(request.userHandle, request.platform)

        return ResponseEntity(submissions, HttpStatus.OK)
    }

    @GetMapping("/get-user-submissions")
    fun getAllUserSubmissions(@RequestBody request: GetAllSubmissionFromUserRequest):
            ResponseEntity<List<Submission>> {
        val submissions = submissionService.getAllSubmissionsFromUser(request.username)

        return ResponseEntity(submissions, HttpStatus.OK)
    }

    @GetMapping("/get-remote-submissions")
    fun getRemoteSubmissionsFromPlatform(@RequestBody request: GetAllSubmissionFromHandleRequest):
            HttpStatus {
        return try {
            submissionService.mergeSubmissionsFromPlatformAndUserHandle(request.userHandle, request.platform)
            HttpStatus.OK
        } catch (e: Exception) {
            HttpStatus.UNAUTHORIZED
        }
    }

}