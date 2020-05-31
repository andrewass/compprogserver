package com.compprogserver.entity.problem

import com.compprogserver.entity.Platform
import javax.persistence.*

@Entity
@Table(name = "T_PROBLEM")
data class Problem(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Enumerated(EnumType.STRING)
        val problemTag: ProblemTag? = null,

        @Enumerated(EnumType.STRING)
        val platform: Platform? = null,

        @OneToMany(mappedBy = "problem")
        val submissions: List<Submission> = mutableListOf()
)