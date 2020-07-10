package com.compprogserver.entity

enum class Platform(val decode : String) {


    CODEFORCES("Codeforces"),

    CODECHEF("Codechef"),

    KATTIS("Kattis"),

    UVA("UVa Online Judge"),

    SPOJ("Sphere Online Judge");

    companion object {
        private val map = values().associateBy(Platform::decode)
        fun fromDecode(decode : String) = map[decode]
     }
}