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
        private val userRepository: UserRepository,
        private val submissionService: SubmissionService
) {

    fun getUserHandlesFromUsername(username: String): List<UserHandle> {
        val user = userRepository.findByUsername(username).get()
        return userHandleRepository.findAllUserHandlesForUser(user)
    }

    fun addUserHandle(request: AddUserHandleRequest) {
        val platform = Platform.fromDecode(request.platform) ?: return

        if (userHandleNotExists(request)) {
            val userHandle = UserHandle(userHandle = request.userHandle, platform = platform)
            val user = userRepository.findByUsername(request.username).get()
            userHandle.user = user
            user.userHandles.add(userHandle)
            userRepository.save(user)
            getSubmissionsFromUserHandle(user.userHandles.last(), platform)
        }
    }

    private fun userHandleNotExists(request: AddUserHandleRequest): Boolean {
        return !userHandleRepository.existsByUserHandleAndPlatform(request.userHandle, Platform.CODEFORCES)
    }

    private fun getSubmissionsFromUserHandle(userHandle: UserHandle, platform: Platform) {
        submissionService.getSubmissionsByUserHandleAndPlatform(userHandle, platform)
    }
}