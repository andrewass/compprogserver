package com.compprogserver.common

import com.compprogserver.repository.*
import com.compprogserver.service.SubmissionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var contestRepository: ContestRepository

    @Autowired
    protected lateinit var problemRatingRepository: ProblemRatingRepository

    @Autowired
    protected lateinit var problemRepository: ProblemRepository

    @Autowired
    protected lateinit var userHandleRepository: UserHandleRepository

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    companion object {
        @Container
        val container = CustomMySQLContainer()
                .withUsername("root")
                .withPassword("")
    }

    fun clearRepositories(){
        problemRatingRepository.deleteAll()
        userRepository.deleteAll()
        contestRepository.deleteAll()
        problemRepository.deleteAll()
        userHandleRepository.deleteAll()
    }
}