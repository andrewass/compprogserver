package com.compprogserver.repository

import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ContestRepository : JpaRepository<Contest, Long> {

    fun findContestByPlatform(platform: Platform): HashSet<Contest>

    fun findContestByStartTimeAfter(startTime: LocalDateTime): List<Contest>

}