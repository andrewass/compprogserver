package com.compprogserver.repository

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubmissionRepository : JpaRepository<Submission, Long> {

    fun findAllByUserHandle(userHandle: UserHandle): HashSet<Submission>

    @Query("select s.id from Submission s where s.userHandle = ?1")
    fun findAllSubmissionsForUserHandleById(userHandle: UserHandle) : Set<Long>
}