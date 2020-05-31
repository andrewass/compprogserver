package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.repository.UserHandleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CodeforcesService @Autowired constructor(
        private val codeforcesConsumer: CodeforcesConsumer,
        private val userHandleRepository: UserHandleRepository
){

    fun getUserHandle(userName: String): UserHandle? {
        val fetchedHandle = codeforcesConsumer.getHandle(userName) ?: return null
        val persistedUserHandle = userHandleRepository.findUserHandleByUsernameAndPlatform(userName, Platform.CODEFORCES)
        return if(persistedUserHandle == null){
            userHandleRepository.save(fetchedHandle)
        } else {
            updatePersistedHandle(persistedUserHandle, fetchedHandle)
            userHandleRepository.save(persistedUserHandle)
        }
    }

    fun getUserSubmissions(username: String): List<Submission> {
        val userSubmissions = codeforcesConsumer.getUserSubmissions(username)
        return userSubmissions
    }

    private fun updatePersistedHandle(persistedUserHandle: UserHandle, fetchedHandle: UserHandle) {
        fetchedHandle.copyTo(persistedUserHandle)
    }

}