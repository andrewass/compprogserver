package com.compprogserver.controller

import com.compprogserver.entity.Contest
import com.compprogserver.entity.Handle
import com.compprogserver.service.CodeforcesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/codeforces")
class CodeforcesController @Autowired constructor(
        private val codeforcesService: CodeforcesService
) {

    @GetMapping("/contests")
    fun getAllContests() : ResponseEntity<List<Contest>>{
        val contests = codeforcesService.getContests()
        return ResponseEntity(contests, HttpStatus.OK)
    }

    @GetMapping("/handle")
    fun getHandle(@RequestParam("handle") handle : String) : ResponseEntity<Handle> {
        val handle = codeforcesService.getHandle(handle)
        return ResponseEntity(HttpStatus.OK)
    }


}