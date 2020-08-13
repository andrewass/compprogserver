package com.compprogserver.consumer

import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.exception.UserHandleNotFoundException

fun convertToUserHandleUva(response: String, username: String): UserHandle {
    return UserHandle(
            userHandle = username,
            externalId = response.toLong(),
            platform = Platform.UVA
    )
}