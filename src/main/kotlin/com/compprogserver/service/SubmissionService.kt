package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.repository.ProblemRepository
import com.compprogserver.repository.SubmissionRepository
import com.compprogserver.repository.UserHandleRepository
import com.compprogserver.repository.UserRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class SubmissionService @Autowired constructor(
        private val submissionRepository: SubmissionRepository,
        private val problemRepository: ProblemRepository,
        private val codeforcesConsumer: CodeforcesConsumer,
        private val userHandleRepository: UserHandleRepository,
        private val userRepository: UserRepository
) : GraphQLQueryResolver {

    fun fetchRemoteSubmissionsFromPlatform(userHandleName: String, platformName: String, username: String)
            : Collection<Submission> {
        val platform = Platform.fromDecode(platformName) ?: throw EntityNotFoundException("Platform : $platformName")
        val user = userRepository.findByUsername(username).get()
        val userHandle = userHandleRepository.findByUserHandleAndPlatformAndUser(
                userHandle = userHandleName, platform = platform, user = user)
        val allSubmissions = submissionRepository.findAllByUserHandle(userHandle.get()).toMutableSet()

        if (platform == Platform.CODEFORCES) {
            val fetchedSubmissions = codeforcesConsumer.getUserSubmissions(userHandle.get())
            allSubmissions.addAll(fetchedSubmissions)
            attachSubmissionsToProblems(allSubmissions)
        }

        return allSubmissions
    }

    fun getAllSubmissionsFromUserHandle(userHandleName: String, platformName: String): List<Submission> {
        val platform = Platform.fromDecode(platformName) ?: throw EntityNotFoundException("Platform : $platformName")
        val userHandle = userHandleRepository.findByUserHandleAndPlatform(userHandleName, platform)

        return submissionRepository.findAllByUserHandle(userHandle.get())
    }

    fun getAllSubmissionsFromProblem(problemName : String, platformName : String) : List<Submission> {
        return emptyList()
    }

    fun findAllProblemsByUserHandleSubmissions(userHandle: UserHandle) =
            submissionRepository.findAllProblemIdFromSubmissionsByUserHandleBy(userHandle)

    private fun attachSubmissionsToProblems(submissions: Set<Submission>) {
        for (submission in submissions) {
            if (submission.id == null) {
                val problem = problemRepository.findByProblemName(submission.problem.problemName)
                        ?: problemRepository.save(submission.problem)
                submission.problem = problem
                problem.submissions.add(submission)
                problemRepository.save(problem)
            }
        }
    }

}