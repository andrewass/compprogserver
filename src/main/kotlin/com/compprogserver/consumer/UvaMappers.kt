package com.compprogserver.consumer

import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle

fun convertToUserHandleUva(response: String, handleName: String, user: User): UserHandle {
    return UserHandle(
            userHandle = handleName,
            externalId = response.toLong(),
            platform = Platform.UVA,
            user = user
    )
}