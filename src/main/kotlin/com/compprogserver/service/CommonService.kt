package com.compprogserver.service

import com.compprogserver.entity.Platform
import org.springframework.stereotype.Service

@Service
class CommonService {

    fun getAllPlatforms(): List<String> {
        return Platform.values()
                .map { it.decode }
                .toList()
    }
}