package com.compprogserver.entity

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
        val email: String = ""
)