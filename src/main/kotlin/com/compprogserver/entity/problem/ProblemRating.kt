package com.compprogserver.entity.problem

import com.compprogserver.entity.User
import org.springframework.boot.autoconfigure.security.SecurityProperties
import javax.persistence.*


@Entity
@Table(name = "T_PROBLEM_RATING")
class ProblemRating(

    @EmbeddedId
    val id : ProblemRatingKey,

    @ManyToOne
    @MapsId("problemId")
    @JoinColumn(name = "problem_id")
    val problem: Problem,

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    val user: User,

    var rating : Int
)