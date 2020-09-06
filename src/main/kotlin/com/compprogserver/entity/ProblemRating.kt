package com.compprogserver.entity

import com.compprogserver.entity.problem.Problem
import javax.persistence.*

@Entity
@Table(name = "T_PROBLEM_RATING")
class ProblemRating(

        @Id
        @Column(name = "PROBLEM_RATING_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "PROBLEM_ID")
        val problem: Problem,

        @ManyToOne
        @JoinColumn(name = "USER_ID")
        val user: User,

        var rating: Int
)