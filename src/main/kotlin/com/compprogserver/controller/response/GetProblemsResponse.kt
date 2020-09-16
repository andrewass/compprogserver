package com.compprogserver.controller.response

import com.compprogserver.entity.problem.ProblemWrapper

class GetProblemsResponse(
        val totalPages: Int = 0,
        val totalElements: Long = 0L,
        val problems: List<ProblemWrapper>
)