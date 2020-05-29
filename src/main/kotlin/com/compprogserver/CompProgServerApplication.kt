package com.compprogserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CompProgServerApplication

fun main(args: Array<String>) {
    runApplication<CompProgServerApplication>(*args)
}
