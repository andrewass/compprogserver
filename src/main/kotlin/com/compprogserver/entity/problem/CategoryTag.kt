package com.compprogserver.entity.problem

import com.fasterxml.jackson.annotation.JsonValue

enum class CategoryTag(@JsonValue val representation : String, val decodes: List<String>) {
    BRUTE_FORCE("Brute Force", listOf("Brute Force, brute force")),
    DATA_STRUCTURE("Data Structure", listOf("Data Structure, data structures")),
    MATH("Math", listOf("Math, math")),
    TREE("Tree", listOf("Tree, trees")),
    GRAPH("Graph", listOf("Graph","graphs")),
    BINARY_SEARCH("Binary Search", listOf("Binary Search","binary search")),
    GREEDY("Greedy", listOf("Greedy","greedy")),
    DYNAMIC_PROGRAMMING("Dynamic Programming", listOf("Dynamic Programming", "dp")),
    IMPLEMENTATION("Implementation", listOf("Implementation", "implementation")),
    DSU("Disjoint Set", listOf("Disjoint Set","dsu")),
    DFS("Dfs", listOf("Dfs","dfs and similar")),
    STRING("String", listOf("String","strings", "string"));

    companion object {
        private val map = mutableMapOf<String, CategoryTag>()

        init {
            for (problemTag in values()) {
                problemTag.decodes.forEach { map[it] = problemTag }
            }
        }

        fun fromDecode(decode: String) = map[decode]
    }
}