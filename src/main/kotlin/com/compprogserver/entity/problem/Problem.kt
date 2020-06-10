package com.compprogserver.entity.problem

import com.compprogserver.entity.Platform
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_PROBLEM")
data class Problem(

        @Id
        @Column(name = "PROBLEM_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        val problemTag: ProblemTag? = null,

        @Enumerated(EnumType.STRING)
        val platform: Platform? = null,

        @Column(name = "problem_name", nullable = false)
        val problemName: String = "",

        val problemUrl : String = "",

        @JsonIgnore
        @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL])
        val submissions: MutableList<Submission> = mutableListOf()
) {
    override fun hashCode() = problemName.hashCode()

    override fun equals(other: Any?): Boolean {
        return if (other is Problem) {
            other.problemName == problemName
        } else {
            false
        }
    }
}