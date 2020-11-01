package com.compprogserver.service

import com.compprogserver.common.AbstractIntegrationTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser

//@GraphQLTest
@WithMockUser
class UserServiceTest : AbstractIntegrationTest(){

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate


    @Test
    fun `random test`() {
        assertEquals(5, 5)
    }
}