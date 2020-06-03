package com.compprogserver.repository

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubmissionRepository : JpaRepository<Submission, Long> {

    fun findAllByUserhandle(userHandle: UserHandle): HashSet<Submission>
}