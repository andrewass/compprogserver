package com.compprogserver.controller.response

import com.compprogserver.entity.problem.Problem

class GetProblemsResponse(
        val totalPages: Int = 0,
        val totalElements: Long = 0L,
        val problems: List<Problem>
)