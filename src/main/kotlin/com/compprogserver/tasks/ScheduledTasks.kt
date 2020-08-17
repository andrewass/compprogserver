package com.compprogserver.tasks

import com.compprogserver.entity.Platform
import com.compprogserver.service.ContestService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@Profile("mysql", "docker")
class ScheduledTasks @Autowired constructor(
        private val  contestService: ContestService
) {
    private val log = LoggerFactory.getLogger(ScheduledTasks::class.java)

    @Scheduled(fixedRate = 100000)
    fun getContests() {
        log.info("Fetching contests")
        contestService.getContestsFromPlatform(Platform.CODEFORCES)
    }

    @Scheduled(fixedRate = 10000)
    fun updatePersistedProblemsFromUserHandles() {
        log.info("Updating problems from user handles")
    }
}