package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CodeforcesConsumer @Autowired constructor(
        private val commonConsumer: CommonConsumer
) {
    val submissionCount = "1000000"

    @Value(value = "\${codeforces.url}")
    lateinit var codeforcesUrl: String

    fun getHandle(username: String): UserHandle? {
        val url = "$codeforcesUrl/user.info"
        val response = commonConsumer.exchange(url, Pair("handles", username))
        return if (response.statusCode.is2xxSuccessful) {
            convertToUserHandle(response.body!!)
        } else {
            null
        }
    }

    fun getUserSubmissions(username: String): List<Submission> {
        val url = "$codeforcesUrl/user.status"
        val response = commonConsumer.exchange(url, Pair("handle", username),
                Pair("from", "1"), Pair("count", submissionCount))
        return if (response.statusCode.is2xxSuccessful) {
            convertToSubmission(response.body!!).toList()
        } else {
            emptyList()
        }
    }
}