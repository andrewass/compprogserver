package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserHandleRepository : JpaRepository<UserHandle, Long> {

    fun findUserHandleByUserHandle(userHandle: String): List<UserHandle>

    fun findUserHandleByUserHandleAndPlatform(userHandle: String, platform: Platform): UserHandle?

    fun existsByUserHandleAndPlatform(username: String, platform: Platform): Boolean

    @Query("SELECT uh FROM UserHandle uh inner join uh.user u where u = ?1")
    fun findAllUserHandlesForUser(user : User): List<UserHandle>
}