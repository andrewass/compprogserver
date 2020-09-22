package com.compprogserver.entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "T_USER_HANDLE")
class UserHandle(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "HANDLE_ID")
        val id: Long? = null,

        val externalId: Long? = null,

        val userHandle: String,

        var rating: Int? = null,

        var maxRating: Int? = null,

        @Column(name = "PLATFORM", nullable = false)
        @Enumerated(EnumType.STRING)
        val platform: Platform,

        @Column(name = "USER_RANK")
        var rank: String? = null,

        @Column(name = "MAX_USER_RANK")
        var maxRank: String? = null,

        @ManyToOne
        @JoinColumn(name = "USER_ID", nullable = false)
        val user: User
) : Serializable {

    fun updateHandle(updated: UserHandle) {
        rating = updated.rating
        maxRating = updated.maxRating
        rank = updated.rank
        maxRank = updated.maxRank
    }
}


