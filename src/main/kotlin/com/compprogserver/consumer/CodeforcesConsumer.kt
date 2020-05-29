package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CodeforcesConsumer (){

    @Value(value = "\${codeforces.url}")
    lateinit var codeforcesUrl : String

    fun getContests() : List<Contest>{
        return emptyList()
    }

}