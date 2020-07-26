package com.compprogserver.consumer

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UvaConsumer @Autowired constructor(
        private val restTemplate: RestTemplate
) : CommonConsumer(restTemplate) {

    @Value(value = "\${codeforces.url}")
    lateinit var uhuntUrl: String

    override fun getUserHandle(username: String): UserHandle? {
        val url = "$uhuntUrl/uname2uid/$username"
        val response = exchange(url)
        return if (response.statusCode.is2xxSuccessful) {
            null
        } else {
            null
        }
    }

    override fun getUserSubmissions(userHandle: UserHandle): Set<Submission> {
        TODO("Not yet implemented")
    }

}