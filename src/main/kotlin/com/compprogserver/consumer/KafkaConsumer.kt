package com.compprogserver.consumer

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer {

    @KafkaListener(topics = ["upcomingContests"], groupId = "groupId")
    fun consumeContests(){

    }

}