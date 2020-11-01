package com.compprogserver.controller

import com.compprogserver.controller.request.AuthenticationRequest
import com.compprogserver.controller.request.SignUpRequest
import com.compprogserver.controller.response.AuthenticationResponse
import com.compprogserver.service.CustomUserService
import com.compprogserver.util.generateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
class AuthenticationController @Autowired constructor(
        private val authenticationManager: AuthenticationManager,
        private val userService: CustomUserService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody request: AuthenticationRequest):
            ResponseEntity<AuthenticationResponse> {
        val response = authenticateAndGenerateJwt(request.username, request.password)

        return ResponseEntity.ok(response)
    }

    @PostMapping("/sign-in")
    fun signInUser(@RequestBody request: AuthenticationRequest) :
            ResponseEntity<AuthenticationResponse> {
        userService.getPersistedUser(request)

        return try {
            val response = authenticateAndGenerateJwt(request.username, request.password)
            ResponseEntity.ok(response)
        } catch (e : AuthenticationException){
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    private fun authenticateAndGenerateJwt(username : String, password : String) : AuthenticationResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        val userDetails = userService.loadUserByUsername(username)

        return AuthenticationResponse(generateToken(userDetails), username)
    }
}