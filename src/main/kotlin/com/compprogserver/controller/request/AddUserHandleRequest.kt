package com.compprogserver.controller.request

data class AddUserHandleRequest(
        val token: String,
        val userHandle: String,
        val platform: String)
