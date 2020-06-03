package com.compprogserver.entity

import javax.persistence.*

@Entity
@Table(name = "T_USER_HANDLE")
class UserHandle(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "HANDLE_ID")
        val id: Long? = null,

        @Column(name = "USERNAME", nullable = false)
        val username: String = "",

        val firstName: String? = null,

        val lastName: String? = null,

        var rating: Int? = null,

        var maxRating: Int? = null,

        var residingCountry: String? = null,

        val emailAddress: String? = null,

        @Column(name = "PLATFORM", nullable = false)
        @Enumerated(EnumType.STRING)
        val platform: Platform? = null,

        @Column(name = "USER_RANK")
        var rank: String? = null,

        @Column(name = "MAX_USER_RANK")
        var maxRank: String? = null
) {

    fun copyTo(destination: UserHandle) {
        destination.rating = rating
        destination.maxRating = maxRating
        destination.rank = rank
        destination.maxRank = maxRank
        destination.residingCountry = residingCountry
    }
}


