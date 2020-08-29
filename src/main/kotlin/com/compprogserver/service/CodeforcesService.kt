package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform.CODEFORCES
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.repository.ContestRepository
import com.compprogserver.repository.ProblemRepository
import com.compprogserver.repository.SubmissionRepository
import com.compprogserver.repository.UserHandleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CodeforcesService @Autowired constructor(
        private val codeforcesConsumer: CodeforcesConsumer,
        private val contestRepository: ContestRepository
) {

    fun getContests(): List<Contest> {
        val allContests = contestRepository.findContestByPlatform(CODEFORCES)
        val fetchedContests = codeforcesConsumer.getContests()
        allContests.addAll(fetchedContests)

        return contestRepository.saveAll(allContests)
    }
}
