package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserHandleRepository : JpaRepository<UserHandle, Long> {

    fun findUserHandleByUsername(username: String): List<UserHandle>

    fun findUserHandleByUsernameAndPlatform(username: String, platform: Platform): UserHandle?
}