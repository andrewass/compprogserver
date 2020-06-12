package com.compprogserver.configuration

import com.compprogserver.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfiguration @Autowired constructor(
        private val customUserDetailsService: CustomUserDetailsService
) : WebSecurityConfigurerAdapter(){

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(customUserDetailsService)
    }

    @Bean
    fun getPassword() : PasswordEncoder {
        //return BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }


}