package com.compprogserver.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfiguration {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun restTemplate() = RestTemplate()
}