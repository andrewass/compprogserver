package com.compprogserver.controller

import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.UserHandle
import com.compprogserver.service.UserHandleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/userhandle")
class UserHandleController @Autowired constructor(
        private val userHandleService: UserHandleService
) {

    @PostMapping("get-userhandles")
    fun getUserHandlesFromUser(@RequestBody request: GetUserHandlesRequest): ResponseEntity<List<UserHandle>> {
        val userHandles = userHandleService.getUserHandlesFromUsername(request.username)
        return ResponseEntity.ok(userHandles)
    }

    @PostMapping("add-userhandle")
    fun addUserHandle(@RequestBody request: AddUserHandleRequest): ResponseEntity<List<UserHandle>> {
        val userHandle = userHandleService.addUserHandle(request)
        return ResponseEntity.ok(userHandle)
    }
}