package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserHandleRepository : JpaRepository<UserHandle, Long> {

    fun findByUserHandleAndPlatform(userHandle: String, platform: Platform): Optional<UserHandle>

    fun findByUserHandleAndPlatformAndUser(userHandle: String, platform: Platform, user: User) : Optional<UserHandle>

    fun findByUserAndPlatform(user: User, platform: Platform) : List<UserHandle>

    fun existsByUserHandleAndPlatform(username: String, platform: Platform): Boolean

    @Query("SELECT uh FROM UserHandle uh inner join uh.user u where u = ?1")
    fun findAllUserHandlesForUser(user : User): List<UserHandle>
}