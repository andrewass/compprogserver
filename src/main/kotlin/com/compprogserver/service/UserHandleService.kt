package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
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
        private val codeforcesConsumer: CodeforcesConsumer
) {

    fun getUserHandlesFromUsername(username: String): List<UserHandle> {
        val user = userRepository.findByUsername(username).get()

        return userHandleRepository.findAllUserHandlesForUser(user)
    }


    fun addUserHandle(request: AddUserHandleRequest) {
        val platform = Platform.fromDecode(request.platform) ?: return

        when (platform) {
            Platform.CODEFORCES -> fetchAndStoreCodeforcesHandle(request)
            else -> addManualUserHandle(request, platform)
        }
    }

    private fun addManualUserHandle(request: AddUserHandleRequest, platform: Platform) {
        if (userHandleNotExists(request.userHandle, platform)) {
            val user = userRepository.findByUsername(request.username).get()
            val userHandle = UserHandle(userHandle = request.userHandle, platform = platform, user = user)
            user.userHandles.add(userHandle)
            userRepository.save(user)
        }
    }

    private fun fetchAndStoreCodeforcesHandle(request: AddUserHandleRequest) {
        val user = userRepository.findByUsername(request.username).get()
        val remoteUserHandle = codeforcesConsumer.getUserHandle(handleName = request.userHandle, user = user) ?: return
        val persistedUserHandle = userHandleRepository.findUserHandleByUserHandleAndPlatform(
                userHandle = remoteUserHandle.userHandle, platform = remoteUserHandle.platform
        )
        if (persistedUserHandle.isEmpty) {
            user.userHandles.add(remoteUserHandle)
            userRepository.save(user)
        } else {
            persistedUserHandle.get().updateHandle(remoteUserHandle)
            userHandleRepository.save(persistedUserHandle.get())
        }
    }

    private fun userHandleNotExists(userHandle: String, platform: Platform): Boolean {
        return !userHandleRepository.existsByUserHandleAndPlatform(userHandle, platform)
    }
}