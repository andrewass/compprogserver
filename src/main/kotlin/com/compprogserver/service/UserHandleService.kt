package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.exception.PlatformNotFoundException
import com.compprogserver.repository.UserHandleRepository
import com.compprogserver.repository.UserRepository
import graphql.kickstart.tools.GraphQLMutationResolver
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserHandleService @Autowired constructor(
        private val userHandleRepository: UserHandleRepository,
        private val userRepository: UserRepository,
        private val codeforcesConsumer: CodeforcesConsumer
) : GraphQLQueryResolver, GraphQLMutationResolver {

    fun getAllUserHandlesByUser(username: String): List<UserHandle> {
        val user = userRepository.findByUsername(username).get()

        return userHandleRepository.findAllUserHandlesForUser(user)
    }


    fun addUserHandle(userHandle: String, platformName: String, username: String) : UserHandle {
        val platform = Platform.fromDecode(platformName) ?: throw PlatformNotFoundException(platformName)

        return when (platform) {
            Platform.CODEFORCES -> fetchAndStoreCodeforcesHandle(userHandle, username).get()
            else -> addManualUserHandle(userHandle, username, platform)
        }
    }

    private fun fetchAndStoreCodeforcesHandle(userHandle: String, username: String) : Optional<UserHandle> {
        val user = userRepository.findByUsername(username).get()
        val remoteUserHandle = codeforcesConsumer.getUserHandle(userHandle, user) ?: return Optional.empty()
        val persistedUserHandle = userHandleRepository.findByUserHandleAndPlatformAndUser(
                userHandle = remoteUserHandle.userHandle, platform = remoteUserHandle.platform, user = user
        )
        return if (persistedUserHandle.isEmpty) {
            user.userHandles.add(remoteUserHandle)
            userRepository.save(user)
            Optional.of(remoteUserHandle)
        } else {
            persistedUserHandle.get().updateHandle(remoteUserHandle)
            userHandleRepository.save(persistedUserHandle.get())
            persistedUserHandle
        }
    }

    private fun addManualUserHandle(userHandleName: String, username: String, platform: Platform) : UserHandle{
        val persistedHandle = userHandleRepository.findByUserHandleAndPlatform(userHandleName, platform)
        return if (persistedHandle.isEmpty) {
            val user = userRepository.findByUsername(username).get()
            val userHandle = UserHandle(userHandle = userHandleName, platform = platform, user = user)
            user.userHandles.add(userHandle)
            userRepository.save(user)
            userHandle
        } else{
            persistedHandle.get()
        }
    }
}