package com.compprogserver.entity.problem

import com.compprogserver.entity.Platform
import com.compprogserver.entity.ProblemRating
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_PROBLEM")
class Problem(

        @Id
        @Column(name = "PROBLEM_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        val platform: Platform,

        val problemName: String,

        val problemUrl: String? = null,

        var rating : Int = 3,

        var rateSum : Long = 3L,

        var rateCount : Int = 1,

        @JsonIgnore
        @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL])
        val submissions: MutableList<Submission> = mutableListOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "problem")
        val ratings : MutableList<ProblemRating> = mutableListOf(),

        @OneToMany(mappedBy = "problem", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val problemTags: MutableList<ProblemTag> = mutableListOf()

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