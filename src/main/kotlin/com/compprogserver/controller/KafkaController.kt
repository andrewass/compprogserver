package com.compprogserver.controller

import com.compprogserver.controller.request.PublishContestRequest
import com.compprogserver.kafka.KafkaProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/kafka/")
class KafkaController @Autowired constructor(
        private val kafkaProducer: KafkaProducer
) {

    @PostMapping("/publish_contest")
    fun publishMessage(@RequestBody request: PublishContestRequest) {
        kafkaProducer.send(request)
    }
}