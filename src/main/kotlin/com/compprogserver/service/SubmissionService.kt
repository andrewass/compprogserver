package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.controller.request.SubmissionRequest
import com.compprogserver.entity.Platform
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
class SubmissionService @Autowired constructor(
        private val submissionRepository: SubmissionRepository,
        private val problemRepository: ProblemRepository,
        private val userHandleRepository: UserHandleRepository,
        private val codeforcesConsumer: CodeforcesConsumer
) {

    fun getSubmissions(request: SubmissionRequest): Collection<Submission> {
        val userHandles = userHandleRepository.findUserHandleByUserHandle(request.handle!!)
        return userHandles.flatMap { submissionRepository.findAllByUserhandle(it) }
    }

    fun getSubmissionsByUserHandleAndPlatform(userHandle: UserHandle, platform: Platform): Set<Submission> {
        val allSubmissions = submissionRepository.findAllByUserhandle(userHandle)
        if(platform == Platform.CODEFORCES) {
            val fetchedSubmissions = codeforcesConsumer.getUserSubmissions(userHandle)
            allSubmissions.addAll(fetchedSubmissions)
            attachSubmissionsToProblems(allSubmissions)
            return fetchedSubmissions
        } else{
            return emptySet()
        }
    }

    private fun attachSubmissionsToProblems(submissions: Set<Submission>) {
        for (submission in submissions) {
            if (submission.id == null) {
                val problem = problemRepository.findByProblemName(submission.problem!!.problemName)
                        ?: problemRepository.save(submission.problem!!)
                submission.problem = problem
                problem.submissions.add(submission)
                problemRepository.save(problem)
            }
        }
    }
}