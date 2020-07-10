package com.compprogserver.service

import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.controller.request.GetUserHandlesRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.repository.UserHandleRepository
import com.compprogserver.repository.UserRepository
import com.compprogserver.util.extractUsername
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserHandleService @Autowired constructor(
        private val userHandleRepository: UserHandleRepository,
        private val userRepository: UserRepository
) {

    fun getUserHandlesFromUsername(username : String): List<UserHandle> {
        val user = userRepository.findByUsername(username)
        return userHandleRepository.findAllUserHandlesForUser(user!!)
    }

    fun addUserHandle(request: AddUserHandleRequest) {
        if (userHandleNotExists(request)) {
            val userHandle = UserHandle(
                    userHandle = request.userHandle,
                    platform = Platform.fromDecode(request.platform))
            val userName = extractUsername(request.token)
            val user = userRepository.findByUsername(userName)
            if (user != null) {
                user.userHandles.add(userHandle)
                userHandle.user = user
                userRepository.save(user)
            }
        }
    }

    private fun userHandleNotExists(request: AddUserHandleRequest): Boolean {
        return !userHandleRepository.existsByUserHandleAndPlatform(request.userHandle, Platform.CODEFORCES)
    }
}