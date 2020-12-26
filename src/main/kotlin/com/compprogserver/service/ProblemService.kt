package com.compprogserver.service

import com.compprogserver.controller.request.AddProblemRatingRequest
import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.controller.request.GetProblemsByTagsRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.ProblemRating
import com.compprogserver.entity.User
import com.compprogserver.entity.problem.CategoryTag
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.ProblemWrapper
import com.compprogserver.entity.problem.Verdict
import com.compprogserver.exception.EntityNotFoundException
import com.compprogserver.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProblemService @Autowired constructor(
        private val problemRepository: ProblemRepository,
        private val userHandleService: UserHandleService,
        private val submissionService: SubmissionService,
        private val userRepository: UserRepository,
        private val problemRatingRepository: ProblemRatingRepository,
        private val userHandleRepository: UserHandleRepository,
        private val submissionRepository: SubmissionRepository
) {

    fun getPopularProblems(username: String?, page: Int, size: Int): Page<ProblemWrapper> {
        val pageable = PageRequest.of(page, size, Sort.by("rating"))
        val problems = problemRepository.findAll(pageable)

        return wrapProblems(problems, username)
    }

    fun getPopularProblemsByTags(request: GetProblemsByTagsRequest): Page<ProblemWrapper> {
        val pageable = PageRequest.of(request.page, request.size, Sort.by("rating"))
        val problems = if (request.categoryTags.isEmpty()) {
            problemRepository.findAll(pageable)
        } else {
            problemRepository.findAllProblemsWithGivenTags(request.categoryTags, pageable)
        }

        return wrapProblems(problems, request.username)
    }

    fun addProblem(request: AddProblemRequest) {
        val platform = Platform.fromDecode(request.platform)
                ?: throw EntityNotFoundException("Platform : ${request.platform}")

        if (!problemRepository.existsByProblemNameAndPlatform(request.problemName, platform)) {
            val problem = Problem(problemName = request.problemName, platform = platform)
            problemRepository.save(problem)
        }
    }

    fun getAllSolvedProblemsForUser(username: String): List<Long> {
        val userhandles = userHandleService.getUserHandlesFromUsername(username)

        return userhandles.flatMap { submissionService.getAllProblemIdForSubmissionsByUserHandle(it) }
    }

    fun addProblemRating(request: AddProblemRatingRequest) {
        val user = userRepository.findByUsername(request.username).get()
        val problem = problemRepository.findById(request.problemId).get()
        val previousRating = problemRatingRepository.findByProblemAndUser(problem, user)

        if (previousRating.isPresent) {
            val prevRating = previousRating.get()
            updateRatingOfProblem(problem, prevRating.rating, request.rating)
            updateUsersProblemRating(prevRating, request.rating)
        } else {
            updateRatingOfProblem(problem, 0, request.rating)
            createProblemRating(problem, user, request.rating)
        }
    }

    private fun wrapProblems(problems: Page<Problem>, username: String?): Page<ProblemWrapper> {
        return if (username != null) {
            val user = userRepository.findByUsername(username).get()
            problems.map { ProblemWrapper(problem = it, solved = solvedByUser(it, user)) }
        } else {
            problems.map { ProblemWrapper(problem = it) }
        }
    }

    private fun solvedByUser(problem: Problem, user: User): Boolean {
        val userHandles = userHandleRepository.findByUserAndPlatform(user, problem.platform)

        return userHandles.any {
            submissionRepository.existsByUserHandleAndProblemAndVerdict(it, problem, Verdict.SOLVED)
        }
    }

    private fun updateRatingOfProblem(problem: Problem, previousRating: Int, newRating: Int) {
        problem.rateSum += (newRating - previousRating)
        problem.rateSum = kotlin.math.max(problem.rateSum, 1)

        if (previousRating == 0) {
            problem.rateCount++
        }
        problem.rating = (problem.rateSum / problem.rateCount).toInt()
        problemRepository.save(problem)
    }

    private fun updateUsersProblemRating(previousRating: ProblemRating, rating: Int): ProblemRating {
        previousRating.rating = rating

        return problemRatingRepository.save(previousRating)
    }

    private fun createProblemRating(problem: Problem, user: User, rating: Int) {
        val problemRating = ProblemRating(problem = problem, user = user, rating = rating)
        problemRatingRepository.save(problemRating)
    }

    fun getAllProblemTags(): List<CategoryTag> {
        return CategoryTag.values().asList()
    }
}
