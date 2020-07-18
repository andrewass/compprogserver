package com.compprogserver.service

import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.problem.Problem
import com.compprogserver.exception.PlatformNotFoundException
import com.compprogserver.repository.ProblemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProblemService @Autowired constructor(
        private val problemRepository: ProblemRepository,
        private val userHandleService: UserHandleService,
        private val submissionService: SubmissionService
) {

    fun getPopularProblems(page: Int, size: Int): Page<Problem> {
        val pageable: Pageable = PageRequest.of(page, size)
        return problemRepository.findAllByOrderByRatingDesc(pageable)
    }

    fun addProblem(request: AddProblemRequest) {
        val platform = Platform.fromDecode(request.platform)
                ?: throw PlatformNotFoundException("Platform ${request.platform} not found")

        if (!problemRepository.existsByProblemNameAndPlatform(request.problemName, platform)) {
            val problem = Problem(problemName = request.problemName, platform = platform)
            problemRepository.save(problem)
        }
    }

    fun getAllSolvedProblemsForUser(username: String): List<Long> {
        val userhandles = userHandleService.getUserHandlesFromUsername(username)

        return userhandles.flatMap { submissionService.getAllSubmissionsByIdForUserHandle(it) }
    }
}