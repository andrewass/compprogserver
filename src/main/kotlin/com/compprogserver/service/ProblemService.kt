package com.compprogserver.service

import com.compprogserver.entity.problem.Problem
import com.compprogserver.repository.ProblemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProblemService @Autowired constructor(
        private val problemRepository: ProblemRepository
){

    fun getTrendingProblems() : Collection<Problem> {
        return problemRepository.findTrendingProblems()
    }
}