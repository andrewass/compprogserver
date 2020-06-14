package com.compprogserver.controller.filter

import com.compprogserver.service.CustomUserService
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
        private val userService: CustomUserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader("Authorization")

        if (header != null && header.startsWith("Bearer ")) {
            val token = header.split(" ").last()
            val username = extractUsername(token)

            if (SecurityContextHolder.getContext().authentication == null) {
                val userdetails = userService.loadUserByUsername(username)
                if (tokenIsValid(token, userdetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userdetails, null, userdetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        chain.doFilter(request, response)
    }
}