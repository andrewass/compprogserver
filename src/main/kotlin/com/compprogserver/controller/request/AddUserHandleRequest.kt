package com.compprogserver.controller.request

data class AddUserHandleRequest(
        val userHandle: String,
        val platform: String,
        val username: String)
