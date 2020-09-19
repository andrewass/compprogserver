package com.compprogserver.service

import com.compprogserver.controller.request.AddUserHandleRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.repository.UserHandleRepository
import com.compprogserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserHandleService @Autowired constructor(
        private val userHandleRepository: UserHandleRepository,
        private val userRepository: UserRepository
) {

    fun getUserHandlesFromUsername(username: String): List<UserHandle> {
        val user = userRepository.findByUsername(username).get()
        return userHandleRepository.findAllUserHandlesForUser(user)
    }

    fun addUserHandle(request: AddUserHandleRequest) {
        val platform = Platform.fromDecode(request.platform) ?: return

        if (userHandleNotExists(request)) {
            val user = userRepository.findByUsername(request.username).get()
            val userHandle = UserHandle(userHandle = request.userHandle, platform = platform, user = user)
            user.userHandles.add(userHandle)
            userRepository.save(user)
        }
    }

    private fun userHandleNotExists(request: AddUserHandleRequest): Boolean {
        return !userHandleRepository.existsByUserHandleAndPlatform(request.userHandle, Platform.CODEFORCES)
    }
}