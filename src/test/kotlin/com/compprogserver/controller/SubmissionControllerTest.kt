package com.compprogserver.controller

import com.compprogserver.common.AbstractIntegrationTest
import com.compprogserver.consumer.CodeforcesConsumer
import com.compprogserver.controller.request.GetSubmissionsFromHandleRequest
import com.compprogserver.entity.Platform
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.Submission
import com.compprogserver.entity.problem.Verdict
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime.now

@WithMockUser
@AutoConfigureMockMvc
internal class SubmissionControllerTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var codeforcesConsumerMock: CodeforcesConsumer

    private val userHandleName = "testHandle"
    private val username = "testUser"

    @BeforeEach
    fun setup() {
        clearRepositories()
    }

    @Test
    fun `should get all submissions from userhandle and status ok`() {
        val user = User(username = username)
        val problem1 = Problem(problemName = "KattisProblem1", platform = Platform.KATTIS)
        val problem2 = Problem(problemName = "KattisProblem2", platform = Platform.KATTIS)
        val userHandle = UserHandle(userHandle = userHandleName, platform = Platform.KATTIS, user = user)
        val submissions = listOf(
                Submission(problem = problem1, userHandle = userHandle, verdict = Verdict.SOLVED,
                        submitted = now().minusDays(1), remoteId = 1),
                Submission(problem = problem2, userHandle = userHandle, verdict = Verdict.SOLVED,
                        submitted = now(), remoteId = 2)
        )
        problem1.submissions.add(submissions[0])
        problem2.submissions.add(submissions[1])

        userRepository.save(user)
        userHandleRepository.save(userHandle)
        problemRepository.save(problem1)
        problemRepository.save(problem2)

        mockMvc.perform(MockMvcRequestBuilders.post("/submission/get-handle-submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetSubmissionsFromHandleRequest("Kattis")))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].problem.problemName")
                        .value("KattisProblem1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].problem.problemName")
                        .value("KattisProblem2"))
    }

    @Test
    fun `should fetch remote submissions from codeforces with status ok`() {
        val user = User(username = username)
        val userHandle = UserHandle(userHandle = userHandleName, platform = Platform.CODEFORCES, user = user)

        userRepository.save(user)
        userHandleRepository.save(userHandle)

        every {
            codeforcesConsumerMock.getUserSubmissions(any())
        } returns setOf(
                Submission(problem = Problem(problemName = "CfProblem1", platform = Platform.CODEFORCES), remoteId = 1,
                        userHandle = userHandle, verdict = Verdict.SOLVED, submitted = now().minusDays(1)),
                Submission(problem = Problem(problemName = "CfProblem2", platform = Platform.CODEFORCES), remoteId = 2,
                        userHandle = userHandle, verdict = Verdict.SOLVED, submitted = now())
        )

        mockMvc.perform(MockMvcRequestBuilders.post("/submission/fetch-remote-submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetSubmissionsFromHandleRequest("Codeforces")))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].problem.problemName")
                        .value("CfProblem1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].problem.problemName")
                        .value("CfProblem2"))
    }

    @Test
    fun `should return status unauthorized when userhandle is not found when fetcing persisted submissions`() {
        val user = User(username = username)

        userRepository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.post("/submission/get-handle-submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetSubmissionsFromHandleRequest("Kattis")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `should return status unauthorized when userhandle is not found when fetcing remote submissions`() {
        val user = User(username = username)

        userRepository.save(user)

        mockMvc.perform(MockMvcRequestBuilders.post("/submission/fetch-remote-submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createGetSubmissionsFromHandleRequest("Codeforces")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    private fun createGetSubmissionsFromHandleRequest(platform: String): String {
        val request = GetSubmissionsFromHandleRequest(userHandleName, platform, username)

        return objectMapper.writeValueAsString(request)
    }
}