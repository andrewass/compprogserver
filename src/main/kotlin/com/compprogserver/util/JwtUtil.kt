package com.compprogserver.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

private const val SECRET_KEY = "secret"


fun isTokenExpired(token: String) = false

/**
 * Generate token for a given user
 */
fun generateToken(userDetails: UserDetails): String {
    val claims = hashMapOf<String, Any>()
    return createToken(userDetails.username, claims)
}


private fun createToken(subject: String, claims: HashMap<String, Any>): String {
    return Jwts.builder().setSubject(subject).setClaims(claims)
            .setIssuedAt(Date()).setExpiration(Date(System.currentTimeMillis() + 600000))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()
}


/**
 * Extract all claims from the token
 */
private fun <T> extractClaimFromToken(token : String) : String {
    val claims =  Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token)
    return ""
}