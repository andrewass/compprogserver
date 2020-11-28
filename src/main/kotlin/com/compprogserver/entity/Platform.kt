package com.compprogserver.entity

import com.fasterxml.jackson.annotation.JsonValue

enum class Platform(@JsonValue val decode: String) {

    CODEFORCES("Codeforces"),

    CODECHEF("Codechef"),

    KATTIS("Kattis"),

    UVA("UVa Online Judge"),

    SPOJ("Sphere Online Judge");

    companion object {
        private val map = values().associateBy(Platform::decode)

        fun fromDecode(decode: String) = map[decode]
    }
}