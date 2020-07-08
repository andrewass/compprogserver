package com.compprogserver.controller

import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.controller.request.GetUserHandleRequest
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
    fun getUserHandlesFromToken(@RequestBody request: GetUserHandleRequest): ResponseEntity<List<UserHandle>> {
        val userHandles = userHandleService.getUserHandlesFromToken(request.token)
        return ResponseEntity.ok(userHandles)
    }

    @PostMapping("add-userhandle")
    fun addUserHandle(@RequestBody request: AddUserHandleRequest): ResponseEntity<List<UserHandle>> {
        userHandleService.addUserHandle(request)
        val userHandles = userHandleService.getUserHandlesFromToken(request.token)
        return ResponseEntity.ok(userHandles)
    }
}