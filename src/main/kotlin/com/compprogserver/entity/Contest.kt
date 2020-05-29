package com.compprogserver.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_CONTEST")
data class Contest(
        @Id
        @GeneratedValue
        val id: Long? = null,

        val name : String = "",

        val description : String = ""
)