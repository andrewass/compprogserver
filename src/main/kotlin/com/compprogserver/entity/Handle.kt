package com.compprogserver.entity

import javax.persistence.*

@Entity
@Table(name = "T_HANDLE")
data class Handle(

        @Id
        @GeneratedValue
        @Column(name = " HANDLE_ID")
        val id: Long? = null,

        val username : String,

        val firstName : String? = null,

        val lastName : String? = null,

        val rating : Int? = null,

        val residingCountry : String? = null,

        val emailAddress : String? = null,

        @Enumerated(EnumType.STRING)
        val platform: Platform? = null,

        val rank : String? = null,

        val maxRank : String? = null
)


