package com.compprogserver.entity.problem

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CategoryTagTest{

    @Test
    fun shouldMapDecodesToStringEnum(){
        assertEquals(CategoryTag.STRING, CategoryTag.fromDecode("string"))
        assertEquals(CategoryTag.STRING, CategoryTag.fromDecode("strings"))
    }
}