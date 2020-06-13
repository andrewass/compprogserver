package com.compprogserver.controller.filter

import com.compprogserver.service.CustomUserDetailsService
import com.compprogserver.util.extractUsername
import com.compprogserver.util.tokenIsValid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter @Autowired constructor(
        private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        var token: String? = null
        var username: String? = null

        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ").last()
            username = extractUsername(token)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userdetails = userDetailsService.loadUserByUsername(username)
            if (tokenIsValid(token!!, userdetails)) {
                val authToken = UsernamePasswordAuthenticationToken(userdetails, null, userdetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        chain.doFilter(request,response)
    }
}