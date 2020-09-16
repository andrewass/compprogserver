package com.compprogserver.entity

import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.Submission
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "T_USER")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "USER_ID")
        val id: Long? = null,

        @Column(nullable = false)
        val username: String = "",

        @Column(nullable = false)
        val password: String = "",

        @Column(nullable = false)
        val email: String = "",

        @JsonIgnore
        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        val userHandles: MutableList<UserHandle> = mutableListOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        val problemRatings: MutableList<ProblemRating> = mutableListOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        val submissions: MutableList<Submission> = mutableListOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        val solvedProblems: MutableList<Problem> = mutableListOf()

) {
    fun addUserHandle(userHandle: UserHandle) {
        userHandles.add(userHandle)
    }

    fun addUserHandles(userHandleList: List<UserHandle>) {
        userHandles.addAll(userHandleList)
    }

    override fun hashCode() = username.hashCode()

    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            other.username == username
        } else {
            false
        }
    }
}