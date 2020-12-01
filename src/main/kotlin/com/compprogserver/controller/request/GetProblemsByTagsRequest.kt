package com.compprogserver.controller.request

import com.compprogserver.entity.problem.CategoryTag

class GetProblemsByTagsRequest(
        val username: String? = null,
        val page: Int = 0,
        val size: Int = 15,
        val categoryTags: List<CategoryTag> = ArrayList()
)
