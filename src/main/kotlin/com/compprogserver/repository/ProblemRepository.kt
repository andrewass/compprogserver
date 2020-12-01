package com.compprogserver.repository

import com.compprogserver.entity.Platform
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.CategoryTag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProblemRepository : JpaRepository<Problem, Long> {

    fun findByProblemName(problemName: String) : Problem?

    @Query("select distinct p from Problem p inner join p.problemTags pt where pt.categoryTag in :tags")
    fun findAllProblemsWithGivenTags(@Param("tags") categoryTags : List<CategoryTag>, pageable: Pageable) :
            Page<Problem>

    fun existsByProblemNameAndPlatform(problemName: String, platform: Platform) : Boolean
}