package com.compprogserver.entity

import com.compprogserver.entity.problem.Submission
import com.fasterxml.jackson.annotation.JsonIgnore
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

        @JsonIgnore
        @OneToMany(mappedBy = "userHandle", cascade = [CascadeType.ALL])
        val submissions: MutableList<Submission> = mutableListOf(),

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

    override fun hashCode(): Int {
        return userHandle.hashCode() + platform.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if(other is UserHandle){
            other.userHandle == userHandle && other.platform == platform
        } else {
            false
        }
    }
}


