package com.compprogserver.controller

import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.controller.request.AddProblemRatingRequest
import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.Platform.CODEFORCES
import com.compprogserver.entity.ProblemRating
import com.compprogserver.entity.User
import com.compprogserver.entity.problem.Problem
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@WithMockUser
@AutoConfigureMockMvc
internal class ProblemControllerTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "testUser"
    private val newRating = 5
    private val problemName = "testProblem"

    @BeforeEach
    fun setUp() {
        clearRepositories()
    }

    @Test
    fun `should get list of solved problems id and status ok`(){
        //TODO:Complete test implementation
    }

    @Test
    fun `should get list of popular problems`() {
        val problems = listOf(
                Problem(problemName = "problem1", platform = CODEFORCES),
                Problem(problemName = "problem2", platform = CODEFORCES),
                Problem(problemName = "problem3", platform = CODEFORCES),
                Problem(problemName = "problem4", platform = CODEFORCES)
        )
        problemRepository.saveAll(problems)

        mockMvc.perform(get("/problem/popular-problems")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("totalElements").value(4))
                .andExpect(jsonPath("problems", hasSize<Any>(4)))
    }

    @Test
    fun `should return status ok when adding a problem`() {
        val request = AddProblemRequest(problemName, "Kattis")
        val jsonRequest = objectMapper.writeValueAsString(request)

        mockMvc.perform(post("/problem/add-problem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val persistedProblem = problemRepository.findByProblemName(problemName)
        assertEquals(persistedProblem!!.problemName, problemName)
    }

    @Test
    fun `should return status not found when adding problem of non-existing platform`(){
        val request = AddProblemRequest(problemName, "TestPlatform")
        val jsonRequest = objectMapper.writeValueAsString(request)

        mockMvc.perform(post("/problem/add-problem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should return status ok when updating existing rating`() {
        val previousRating = createPreviousProblemRating()

        mockMvc.perform(post("/problem/add-problem-rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddProblemRatingRequest(previousRating.problem.id!!)))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val updatedProblemRating = problemRatingRepository
                .findByProblemAndUser(previousRating.problem, previousRating.user).get()

        assertEquals(newRating, updatedProblemRating.rating)
        assertEquals(previousRating.user, updatedProblemRating.user)
        assertEquals(previousRating.problem, updatedProblemRating.problem)
    }

    @Test
    fun `should return status ok when creating new rating`() {
        val userProblemPair = createUserAndProblem()

        mockMvc.perform(post("/problem/add-problem-rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddProblemRatingRequest(userProblemPair.second.id!!)))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val updatedProblemRating = problemRatingRepository
                .findByProblemAndUser(userProblemPair.second, userProblemPair.first).get()

        assertEquals(newRating, updatedProblemRating.rating)
        assertEquals(userProblemPair.first, updatedProblemRating.user)
        assertEquals(userProblemPair.second, updatedProblemRating.problem)
    }

    @Test
    fun `should return status unauthorized when username does not exist`() {
        val problem = problemRepository.save(Problem(problemName = "testProblem", platform = CODEFORCES))

        mockMvc.perform(post("/problem/add-problem-rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddProblemRatingRequest(problem.id!!)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `should return status unauthorized when problem does not exist`() {
        userRepository.save(User(username = username))

        mockMvc.perform(post("/problem/add-problem-rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createAddProblemRatingRequest(100)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    private fun createAddProblemRatingRequest(problemId: Long): String {
        val request = AddProblemRatingRequest(username, problemId, newRating)

        return objectMapper.writeValueAsString(request)
    }

    private fun createUserAndProblem(): Pair<User, Problem> {
        val user = userRepository.save(User(username = username))
        val problem = problemRepository.save(Problem(problemName = "testProblem", platform = CODEFORCES))

        return Pair(user, problem)
    }

    private fun createPreviousProblemRating(): ProblemRating {
        val user = userRepository.save(User(username = username))
        val problem = problemRepository.save(Problem(problemName = "testProblem", platform = CODEFORCES))
        val problemRating = ProblemRating(problem = problem, user = user, rating = 4)

        return problemRatingRepository.save(problemRating)
    }
}