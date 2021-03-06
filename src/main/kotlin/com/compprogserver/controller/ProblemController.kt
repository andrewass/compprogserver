package com.compprogserver.controller

import com.compprogserver.controller.request.AddProblemRatingRequest
import com.compprogserver.controller.request.AddProblemRequest
import com.compprogserver.controller.request.GetProblemsByTagsRequest
import com.compprogserver.controller.response.GetProblemsResponse
import com.compprogserver.entity.problem.CategoryTag
import com.compprogserver.exception.EntityNotFoundException
import com.compprogserver.service.ProblemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin("*")
@RequestMapping("/problem")
class ProblemController @Autowired constructor(
        private val problemService: ProblemService
) {

    @GetMapping("/all-problem-tags")
    fun getAllProblemTags(): ResponseEntity<List<CategoryTag>> {
        val problemTags = problemService.getAllProblemTags()

        return ResponseEntity.ok(problemTags)
    }

    @GetMapping("/solved-problems")
    fun getSolvedProblemsByIdForUser(@RequestParam username: String): ResponseEntity<List<Long>> {
        val problems = problemService.getAllSolvedProblemsForUser(username)

        return ResponseEntity.ok(problems)
    }

    @GetMapping("/popular-problems")
    fun getProblems(@RequestParam(required = false) username: String?,
                    @RequestParam(defaultValue = "0") page: Int,
                    @RequestParam(defaultValue = "15") size: Int): ResponseEntity<GetProblemsResponse> {
        val responsePage = problemService.getPopularProblems(username, page, size)
        val response = GetProblemsResponse(
                totalElements = responsePage.totalElements,
                totalPages = responsePage.totalPages,
                problems = responsePage.content
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/popular-problems-by-tag")
    fun getProblemsByTags(@RequestBody request: GetProblemsByTagsRequest): ResponseEntity<GetProblemsResponse> {
        val responsePage = problemService.getPopularProblemsByTags(request)
        val response = GetProblemsResponse(
                totalElements = responsePage.totalElements,
                totalPages = responsePage.totalPages,
                problems = responsePage.content
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/add-problem")
    fun addProblem(@RequestBody request: AddProblemRequest): ResponseEntity<HttpStatus> {
        return try {
            problemService.addProblem(request)
            ResponseEntity(HttpStatus.OK)
        } catch (e: EntityNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/add-problem-rating")
    fun addProblemRating(@RequestBody requestProblem: AddProblemRatingRequest): ResponseEntity<HttpStatus> {
        return try {
            problemService.addProblemRating(requestProblem)
            ResponseEntity(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}