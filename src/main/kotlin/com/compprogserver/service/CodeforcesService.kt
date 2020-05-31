package com.compprogserver.service

import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.entity.Contest
import com.compprogserver.entity.Handle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CodeforcesService @Autowired constructor(
        private val codeforcesConsumer: CodeforcesConsumer
){
    fun getContests() : List<Contest>{
        val contests = mutableListOf<Contest>()
        contests.addAll(codeforcesConsumer.getContests(isGymContest = false))
        contests.addAll(codeforcesConsumer.getContests(isGymContest = true))
        return contests
    }

    fun getHandle(handle: String): Handle {
        return Handle(username = "")
    }
}