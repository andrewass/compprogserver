package com.compprogserver.entity.problem

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class ProblemRatingKey(

        @Column(name = "problem_id")
        val problemId: Long,

        @Column(name = "user_id")
        val userId: Long

) : Serializable