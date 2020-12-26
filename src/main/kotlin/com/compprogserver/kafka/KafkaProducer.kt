package com.compprogserver.kafka

import com.compprogserver.controller.request.PublishContestRequest
import com.compprogserver.entity.Contest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer @Autowired constructor(
        private val kafkaTemplate: KafkaTemplate<String, Contest>
) {
    fun send(request: PublishContestRequest) {
        kafkaTemplate.send(request.topic, request.contest)
    }
}