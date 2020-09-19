package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import com.compprogserver.exception.ExternalEndpointException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class CodeforcesConsumer @Autowired constructor(
        restTemplate: RestTemplate
) : CommonConsumer(restTemplate) {

    private val submissionCount = "1000000"

    @Value(value = "\${codeforces.url}")
    private lateinit var codeforcesUrl: String

    fun getUserHandle(handleName: String, user: User): UserHandle? {
        val url = "$codeforcesUrl/user.info"
        val response = exchange(url, Pair("handles", handleName))

        return if (response.statusCode.is2xxSuccessful) {
            convertToUserHandleCF(response.body!!, user)
        } else {
            throw ExternalEndpointException("Userhandle for $handleName was not found")
        }
    }

    fun getUserSubmissions(userHandle: UserHandle): Set<Submission> {
        val url = "$codeforcesUrl/user.status"
        val response = exchange(url, Pair("handle", userHandle.userHandle),
                Pair("from", "1"), Pair("count", submissionCount))

        return if (response.statusCode.is2xxSuccessful) {
            convertToSubmissions(response.body!!, userHandle)
        } else {
            emptySet()
        }
    }

    fun getContests(): Set<Contest> {
        val url = "$codeforcesUrl/contest.list"
        val response = exchange(url, Pair("gym", "false"))

        return if (response.statusCode.is2xxSuccessful) {
            convertToContests(response.body!!)
        } else {
            emptySet()
        }
    }
}