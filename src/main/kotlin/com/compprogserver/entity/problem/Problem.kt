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

        @ElementCollection(fetch = FetchType.EAGER, targetClass = ProblemTag::class)
        @CollectionTable(name = "T_PROBLEM_TAG", joinColumns = [JoinColumn(name = "PROBLEM_ID")])
        @Enumerated(EnumType.STRING)
        @Column(name = "TAG_NAME")
        val problemTags: List<ProblemTag> = ArrayList(),

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
        val ratings : MutableList<ProblemRating> = mutableListOf()

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