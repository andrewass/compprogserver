package com.compprogserver.service

import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.controller.request.AddProblemRatingRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.ProblemRating
import com.compprogserver.exception.PlatformNotFoundException
import com.compprogserver.repository.ProblemRatingRepository
import com.compprogserver.repository.ProblemRepository
import com.compprogserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProblemService @Autowired constructor(
        private val problemRepository: ProblemRepository,
        private val userHandleService: UserHandleService,
        private val submissionService: SubmissionService,
        private val userRepository: UserRepository,
        private val problemRatingRepository: ProblemRatingRepository
) {

    fun getPopularProblems(page: Int, size: Int): Page<Problem> {
        val pageable = PageRequest.of(page, size)

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

        return userhandles.flatMap { submissionService.getAllProblemIdForSubmissionsByUserHandle(it) }
    }

    fun addProblemRating(requestProblem: AddProblemRatingRequest): ProblemRating {
        val user = userRepository.findByUsername(requestProblem.username).get()
        val problem = problemRepository.findById(requestProblem.problemId).get()
        val previousRating = problemRatingRepository.findByProblemAndUser(problem, user)

        return if (previousRating.isPresent) {
            updateProblemRating(previousRating.get(), requestProblem.rating)
        } else {
            createProblemRating(problem, user, requestProblem.rating)
        }
    }

    private fun updateProblemRating(previousRating: ProblemRating, rating: Int) : ProblemRating {
        previousRating.rating = rating

        return problemRatingRepository.save(previousRating)
    }

    private fun createProblemRating(problem: Problem, user: User, rating: Int): ProblemRating {
        val problemRating = ProblemRating(problem = problem, user = user, rating = rating)

        return problemRatingRepository.save(problemRating)
    }

}