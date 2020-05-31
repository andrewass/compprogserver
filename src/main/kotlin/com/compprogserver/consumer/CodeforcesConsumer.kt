package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class CodeforcesConsumer @Autowired constructor(
        private val restTemplate: RestTemplate
) {

    @Value(value = "\${codeforces.url}")
    lateinit var codeforcesUrl : String

    fun getContests(isGymContest : Boolean) : List<Contest>{
        val path = "$codeforcesUrl/contest.list"
        return emptyList()
    }

}