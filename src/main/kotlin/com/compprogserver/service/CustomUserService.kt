package com.compprogserver.service

import com.compprogserver.controller.request.AuthenticationRequest
import com.compprogserver.controller.request.SignUpRequest
import com.compprogserver.controller.response.AuthenticationResponse
import com.compprogserver.entity.Authentication
import com.compprogserver.repository.UserRepository
import com.compprogserver.util.generateToken
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserService @Autowired constructor(
        private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(userName: String): UserDetails {
        val persistedUser = userRepository.findByUsername(userName)

        return User(persistedUser.get().username, persistedUser.get().password, emptyList())
    }

    fun getPersistedUser(request: AuthenticationRequest): com.compprogserver.entity.User {
        return userRepository.findByUsername(request.username).get()
    }
}