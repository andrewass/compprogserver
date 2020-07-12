package com.compprogserver.service

import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.problem.Problem
import com.compprogserver.exception.PlatformNotFoundException
import com.compprogserver.repository.ProblemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProblemService @Autowired constructor(
        private val problemRepository: ProblemRepository
) {

    fun getPopularProblems(): List<Problem> {
        return problemRepository.findPopularProblems()
    }

    fun addProblem(request: AddProblemRequest) {
        val platform = Platform.fromDecode(request.platform)
                ?: throw PlatformNotFoundException("Platform ${request.platform} not found")

        if (!problemRepository.existsByProblemNameAndPlatform(request.problemName, platform)) {
            val problem = Problem(problemName = request.problemName, platform = platform)
            problemRepository.save(problem)
        }
    }
}