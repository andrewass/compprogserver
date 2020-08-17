package com.compprogserver.consumer

import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle

fun convertToUserHandleUva(response: String, username: String): UserHandle {
    return UserHandle(
            userHandle = username,
            externalId = response.toLong(),
            platform = Platform.UVA
    )
}