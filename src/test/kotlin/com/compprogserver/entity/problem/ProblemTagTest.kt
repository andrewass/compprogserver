package com.compprogserver.entity.problem

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ProblemTagTest{

    @Test
    fun shouldMapDecodesToStringEnum(){
        assertEquals(ProblemTag.STRING, ProblemTag.fromDecode("string"))
        assertEquals(ProblemTag.STRING, ProblemTag.fromDecode("strings"))
    }

}