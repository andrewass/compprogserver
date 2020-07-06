package com.compprogserver.service

import com.compprogserver.controller.request.SubmissionRequest
import com.compprogserver.entity.problem.Submission
import com.compprogserver.repository.SubmissionRepository
import com.compprogserver.repository.UserHandleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SubmissionService @Autowired constructor(
        private val submissionRepository: SubmissionRepository,
        private val userHandleRepository: UserHandleRepository
) {

    fun getSubmissions(request: SubmissionRequest): Collection<Submission> {
        val userHandles = userHandleRepository.findUserHandleByUserHandle(request.handle!!)
        return userHandles.flatMap { submissionRepository.findAllByUserhandle(it) }
    }
}