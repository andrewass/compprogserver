package com.compprogserver.controller.request

import com.compprogserver.entity.Platform

class FetchRemoteUserHandlesRequest(
        val userHandle: String,
        val platform: Platform,
        val username: String
)
