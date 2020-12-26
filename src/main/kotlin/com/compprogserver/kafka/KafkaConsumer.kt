package com.compprogserver.kafka

import com.compprogserver.entity.Contest
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {

    private val log = LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["contests"], groupId = "groupId")
    fun receive(contest: Contest) {
        log.info("Receiving contest ${contest.contestName}")
    }
}