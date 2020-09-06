package com.compprogserver.entity

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
        val userHandles : MutableList<UserHandle> = mutableListOf(),

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        val problemRatings : MutableList<ProblemRating> = mutableListOf()
){
        fun addUserHandle(userHandle: UserHandle){
                userHandles.add(userHandle)
        }

        fun addUserHandles(userHandleList: List<UserHandle>){
                userHandles.addAll(userHandleList)
        }
}