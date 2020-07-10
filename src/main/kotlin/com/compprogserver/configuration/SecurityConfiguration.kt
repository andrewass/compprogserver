package com.compprogserver.configuration

import com.compprogserver.controller.filter.JwtRequestFilter
import com.compprogserver.service.CustomUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration @Autowired constructor(
        private val customUserService: CustomUserService,
        private val jwtRequestFilter: JwtRequestFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserService)
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/*", "/codeforces/*", "/problem/*", "/contest/*", "/common/*")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun getPassword(): PasswordEncoder {
        //return BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()
}