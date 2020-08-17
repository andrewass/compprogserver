package com.compprogserver.consumer

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.exception.ExternalEndpointException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UvaConsumer @Autowired constructor(
        restTemplate: RestTemplate
) : CommonConsumer(restTemplate) {

    @Value(value = "\${uhunt.url}")
    private lateinit var uhuntUrl: String
    
    override fun getUserHandle(username: String): UserHandle? {
        val url = "$uhuntUrl/uname2uid/$username"
        val response = exchange(url)

        return if (response.statusCode.is2xxSuccessful) {
            convertToUserHandleUva(response.body!!, username)
        } else {
            throw ExternalEndpointException("Status Code : ${response.statusCode}");
        }
    }

    override fun getUserSubmissions(userHandle: UserHandle): Set<Submission> {
        TODO("Not yet implemented")
    }

}