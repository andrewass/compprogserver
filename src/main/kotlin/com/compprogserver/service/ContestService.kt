package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform
import com.compprogserver.repository.ContestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ContestService @Autowired constructor(
        private val contestRepository: ContestRepository,
        private val codeforcesConsumer: CodeforcesConsumer
) {
    fun getUpcomingContests(): List<Contest> {
        val contests = contestRepository.findContestByStartTimeAfter(LocalDateTime.now())
        return contests.sortedBy { it.startTime }
    }

    fun getContestsFromPlatform(platform: Platform) : List<Contest> {
        val contests = when (platform) {
            Platform.CODEFORCES -> codeforcesConsumer.getContests()
            else -> emptySet()
        }
        val persistedContests = contestRepository.findContestByPlatform(platform)
        persistedContests.addAll(contests)
        return contestRepository.saveAll(persistedContests)
    }
}