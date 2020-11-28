package com.compprogserver.entity.problem

import com.fasterxml.jackson.annotation.JsonValue

enum class ProblemTag(@JsonValue val representation : String, val decodes: List<String>) {
    BRUTE_FORCE("Brute Force", listOf("brute force")),
    DATA_STRUCTURE("Data Structure", listOf("data structures")),
    MATH("Math", listOf("math")),
    TREE("Tree", listOf("trees")),
    GRAPH("Graphs", listOf("graphs")),
    BINARY_SEARCH("Binary Search", listOf("binary search")),
    GREEDY("Greedy", listOf("greedy")),
    DYNAMIC_PROGRAMMING("Dynamic Programming", listOf("Dynamic Programming", "dp")),
    IMPLEMENTATION("Implementation", listOf("implementation")),
    DSU("Disjoint Set", listOf("dsu")),
    DFS("Dfs", listOf("dfs and similar")),
    STRING("String", listOf("strings", "string"));

    companion object {
        private val map = mutableMapOf<String, ProblemTag>()

        init {
            for (problemTag in values()) {
                problemTag.decodes.forEach { map[it] = problemTag }
            }
        }

        fun fromDecode(decode: String) = map[decode]
    }
}