package com.compprogserver.entity

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "T_CONTEST")
class Contest(

        @Id
        @Column(name = "CONTEST_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val remoteId: Int? = null,

        @Column(nullable = false)
        val contestName: String = "",

        val startTime: LocalDateTime? = null,

        val duration: Int? = null,

        @Enumerated(EnumType.STRING)
        val platform: Platform? = null

) : Serializable {

    companion object {
        private const val serialVersionUID = 324235432345L
    }

    override fun hashCode() = remoteId.hashCode()

    override fun equals(other: Any?): Boolean {
        return if (other is Contest) {
            other.remoteId == remoteId
        } else {
            false
        }
    }
}