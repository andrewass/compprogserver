package com.compprogserver.controller

import com.compprogserver.controller.request.PublishContestRequest
import com.compprogserver.entity.Contest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/contest")
class KafkaController @Autowired constructor(
        @Qualifier(value = "contest")
        private val template: KafkaTemplate<String, Contest>
) {

    @PostMapping("/publish")
    fun publishMessage(@RequestBody contestRequest : PublishContestRequest) {
        template.send(contestRequest.topic, contestRequest.contest)
    }
}