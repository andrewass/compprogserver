package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.problem.Problem
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : JpaRepository<Problem, Long> {

    fun findAllByOrderByRatingDesc(pageable: Pageable): Page<Problem>

    fun findByProblemName(problemName: String): Problem?

    fun existsByProblemNameAndPlatform(problemName: String, platform: Platform): Boolean
}