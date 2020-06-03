package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.problem.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : JpaRepository<Problem, Long> {

    fun findAllByPlatform(platform: Platform) : HashSet<Problem>

    fun findByProblemName(problemName : String) : Problem?
}