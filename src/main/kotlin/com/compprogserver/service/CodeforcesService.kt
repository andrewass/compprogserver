package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Platform.CODEFORCES
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
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
        private val userHandleRepository: UserHandleRepository,
        private val submissionRepository: SubmissionRepository,
        private val problemRepository: ProblemRepository
) {

    fun getUserHandle(userName: String): UserHandle? {
        val fetchedHandle = codeforcesConsumer.getHandle(userName) ?: return null
        val persistedUserHandle = userHandleRepository.findUserHandleByUsernameAndPlatform(userName, CODEFORCES)

        return if (persistedUserHandle == null) {
            userHandleRepository.save(fetchedHandle)
        } else {
            updatePersistedHandle(persistedUserHandle, fetchedHandle)
            userHandleRepository.save(persistedUserHandle)
        }
    }

    fun getUserSubmissions(username: String): Set<Submission> {
        val userHandle: UserHandle = userHandleRepository.findUserHandleByUsernameAndPlatform(username, CODEFORCES)
                ?: return emptySet()
        val allSubmissions = submissionRepository.findAllByUserhandle(userHandle)
        val fetchedSubmissions = codeforcesConsumer.getUserSubmissions(userHandle)
        allSubmissions.addAll(fetchedSubmissions)
        attachSubmissionsToProblems(allSubmissions)

        return fetchedSubmissions
    }

    private fun attachSubmissionsToProblems(submissions: Set<Submission>) {
        for(submission in submissions){
            if(submission.id == null){
                val problem = problemRepository.findByProblemName(submission.problem!!.problemName)
                        ?: problemRepository.save(submission.problem!!)
                submission.problem = problem
                problem.submissions.add(submission)
                problemRepository.save(problem)
            }
        }
    }

    private fun updatePersistedHandle(persistedUserHandle: UserHandle, fetchedHandle: UserHandle) {
        fetchedHandle.copyTo(persistedUserHandle)
    }
}