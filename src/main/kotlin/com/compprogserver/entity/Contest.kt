package com.compprogserver.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_CONTEST")
class Contest(
        @Id
        @GeneratedValue
        val id: Long? = null,

        val name: String = "",

        val description: String = ""
) : Serializable {

    companion object {
        private const val serialVersionUID = 324235432345L
    }
}