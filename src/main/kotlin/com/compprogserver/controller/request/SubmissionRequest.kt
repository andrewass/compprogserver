package com.compprogserver.controller.request

import com.compprogserver.entity.Platform

class SubmissionRequest(val handle: String? = null, val platform : List<Platform> = emptyList())