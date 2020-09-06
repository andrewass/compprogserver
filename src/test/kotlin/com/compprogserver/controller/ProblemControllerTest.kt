package com.compprogserver.controller

import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.controller.request.AddProblemRatingRequest
import com.compprogserver.entity.User
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.ProblemRating
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WithMockUser
@AutoConfigureMockMvc
internal class ProblemControllerTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "testUser"
    private val newRating = 5

    @BeforeEach
    fun setUp(){
        clearRepositories()
    }

    @Test
    fun `should return expected status when updating existing rating`(){
        val previousProblemRating = createPreviousProblemRating()

        mockMvc.perform(post("/problem/add-problem-rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddProblemRatingRequest(previousProblemRating.problem.id!!)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun createAddProblemRatingRequest(problemId : Long): String {
        val request = AddProblemRatingRequest(username, problemId, newRating)

        return objectMapper.writeValueAsString(request)
    }

    private fun createPreviousProblemRating() : ProblemRating {
        val user = userRepository.save(User(username = username))
        val problem = problemRepository.save(Problem(problemName = "testProblem"))
        val problemRating = ProblemRating(problem = problem, user = user, rating = 4)

        return problemRatingRepository.save(problemRating)
    }
}