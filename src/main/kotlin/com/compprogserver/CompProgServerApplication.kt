package com.compprogserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CompProgServerApplication

fun main(args: Array<String>) {
    runApplication<CompProgServerApplication>(*args)
}