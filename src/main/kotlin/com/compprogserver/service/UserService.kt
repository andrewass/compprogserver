package com.compprogserver.service

import com.compprogserver.entity.Authentication
import com.compprogserver.entity.User
import com.compprogserver.repository.UserRepository
import com.compprogserver.util.generateToken
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val authenticationManager: AuthenticationManager,
        private val customUserService: CustomUserService
) : GraphQLQueryResolver, GraphQLMutationResolver{

    fun signUpUser(username: String, password: String, email: String) : Authentication {
        val persistedUser = userRepository.findByUsername(username)
        return if(persistedUser.isPresent){
            authenticateAndGenerateJwt(persistedUser.get(), password)
        } else{
            var user = User(username = username, password = passwordEncoder.encode(password), email = email)

            user = userRepository.save(user)
            authenticateAndGenerateJwt(user, password)
        }
    }

    fun getAllUsers() : List<User> {
        return userRepository.findAll()
    }

    private fun authenticateAndGenerateJwt(user : User, password : String) : Authentication {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, password))
        val userDetails = customUserService.loadUserByUsername(user.username)

        return Authentication(user, generateToken(userDetails))
    }
}