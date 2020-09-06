package com.compprogserver.repository

import com.compprogserver.entity.User
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.ProblemRating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProblemRatingRepository : JpaRepository<ProblemRating, Long>{

    fun findByProblemAndUser(problem : Problem, user : User) : Optional<ProblemRating>

}