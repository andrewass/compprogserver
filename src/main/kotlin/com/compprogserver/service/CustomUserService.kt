package com.compprogserver.service

import com.compprogserver.controller.request.AuthenticationRequest
import com.compprogserver.controller.request.SignUpRequest
import com.compprogserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(userName: String): UserDetails {
        val persistedUser = userRepository.findByUsername(userName)

        return User(persistedUser!!.username, persistedUser.password, emptyList())
    }

    fun addNewUser(request: SignUpRequest): com.compprogserver.entity.User? {
        if (userRepository.existsByUsername(request.username)) {
            return null
        }
        val user = com.compprogserver.entity.User(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                email = request.email)

        return userRepository.save(user)
    }

    fun getPersistedUser(request: AuthenticationRequest): com.compprogserver.entity.User? {
        return userRepository.findByUsername(request.username)
    }
}