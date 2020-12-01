package com.compprogserver.entity.problem

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_PROBLEM_TAG")
class ProblemTag(

        @Id
        @Column(name = "PROBLEM_TAG_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "PROBLEM_ID")
        val problem: Problem,

        @Enumerated(EnumType.STRING)
        val categoryTag: CategoryTag
)