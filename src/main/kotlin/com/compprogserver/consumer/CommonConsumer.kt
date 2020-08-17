package com.compprogserver.consumer

import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
abstract class CommonConsumer(private val restTemplate: RestTemplate) {

    abstract fun getUserHandle(username: String): UserHandle?

    abstract fun getUserSubmissions(userHandle: UserHandle): Set<Submission>

    fun exchange(url: String, vararg parameters: Pair<String, String>) =
            restTemplate.exchange(createURI(url, *parameters),
                    HttpMethod.GET,
                    HttpEntity("body", createHeaders()),
                    String::class.java)


    private fun createURI(url: String, vararg parameters: Pair<String, String>): String {
        val uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
        for (parameter in parameters) {
            uriComponentsBuilder.queryParam(parameter.first, parameter.second)
        }

        return uriComponentsBuilder.toUriString()
    }


    private fun createHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")

        return headers
    }
}