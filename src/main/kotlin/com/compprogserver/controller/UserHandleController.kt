package com.compprogserver.controller

import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.UserHandle
import com.compprogserver.service.UserHandleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/userhandle")
class UserHandleController @Autowired constructor(
        private val userHandleService: UserHandleService
) {

    @PostMapping("/get-users-userhandles")
    fun getUserHandlesFromUser(@RequestBody request: GetUserHandlesRequest): ResponseEntity<List<UserHandle>> {
        val userHandles = userHandleService.getUserHandlesFromUsername(request.username)

        return ResponseEntity.ok(userHandles)
    }

    @PostMapping("/add-userhandle")
    fun addUserHandle(@RequestBody request: AddUserHandleRequest): HttpStatus {
        userHandleService.addUserHandle(request)

        return HttpStatus.OK
    }
}