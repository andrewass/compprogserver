package com.compprogserver.configuration

import com.compprogserver.entity.Contest
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration
class KafkaConfiguration {

    @Value(value = "\${kafka.bootstrapAddress}")
    private lateinit var bootstrapAddress: String

    @Value(value = "\${kafka.group.id}")
    private lateinit var groupId: String

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val config = HashMap<String, Any>()
        return KafkaAdmin(config)
    }

    @Bean
    @Qualifier(value = "contest")
    fun kafkaTemplate(): KafkaTemplate<String, Contest> {
        return KafkaTemplate(contestProducerConfig())
    }


    @Bean
    fun contestProducerConfig(): ProducerFactory<String, Contest> {
        val config = HashMap<String, Any>()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    @Qualifier(value = "contest")
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Contest> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Contest>()
        factory.consumerFactory = contestConsumerConfig()

        return factory
    }

    @Bean
    fun contestConsumerConfig(): ConsumerFactory<String, Contest> {
        val config = HashMap<String, Any>()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        config[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java

        return DefaultKafkaConsumerFactory(config)
    }
}