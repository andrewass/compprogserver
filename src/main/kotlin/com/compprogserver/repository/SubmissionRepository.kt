package com.compprogserver.repository

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.Submission
import com.compprogserver.entity.problem.Verdict
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubmissionRepository : JpaRepository<Submission, Long> {

    fun findAllByUserHandle(userHandle: UserHandle): List<Submission>

    @Query("select p.id from Submission s inner join s.problem p where s.userHandle = ?1")
    fun findAllProblemIdFromSubmissionsByUserHandleBy(userHandle: UserHandle): Set<Long>

    fun existsByUserHandleAndProblemAndVerdict(userHandle: UserHandle, problem: Problem, verdict: Verdict): Boolean
}