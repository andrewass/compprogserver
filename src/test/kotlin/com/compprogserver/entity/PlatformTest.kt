package com.compprogserver.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlatformTest{

    @Test
    fun shouldMapCodeforcesEnum(){
        val result = Platform.fromDecode("Codeforces")
        assertEquals(Platform.CODEFORCES, result)
    }
    
    @Test
    fun shouldMapCodechefEnum(){
        val result = Platform.fromDecode("Codechef")
        assertEquals(Platform.CODECHEF, result)
    }
    
    @Test
    fun shouldMapKattisEnum() {
        val result = Platform.fromDecode("Kattis")
        assertEquals(Platform.KATTIS, result)
    }
    
    @Test
    fun shouldMapUvaEnum(){
        val result = Platform.fromDecode("UVa Online Judge")
        assertEquals(Platform.UVA, result)
    }

    @Test
    fun shouldMapSpojEnum(){
        val result = Platform.fromDecode("Sphere Online Judge")
        assertEquals(Platform.SPOJ, result)
    }
}