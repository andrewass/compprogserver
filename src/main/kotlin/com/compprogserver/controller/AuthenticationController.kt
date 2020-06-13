package com.compprogserver.controller

import com.compprogserver.controller.request.AuthenticationRequest
import com.compprogserver.controller.response.AuthenticationResponse
import com.compprogserver.service.CustomUserDetailsService
import com.compprogserver.util.generateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
class AuthenticationController @Autowired constructor(
        private val authenticationManager: AuthenticationManager,
        private val userDetailsService: CustomUserDetailsService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest):
            ResponseEntity<AuthenticationResponse> {
        val result = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password))
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = generateToken(userDetails)

        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }

}