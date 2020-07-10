package com.compprogserver.service

import com.compprogserver.entity.Contest
import com.compprogserver.repository.ContestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ContestService @Autowired constructor(
        private val contestRepository: ContestRepository
) {
    fun getUpcomingContests(): List<Contest> {
        val contests = contestRepository.findContestByStartTimeAfter(LocalDateTime.now())
        return contests.sortedBy { it.startTime }
    }
}