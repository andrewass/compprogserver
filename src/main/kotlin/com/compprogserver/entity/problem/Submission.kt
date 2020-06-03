package com.compprogserver.entity.problem

import com.compprogserver.entity.UserHandle
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "T_SUBMISSION")
class Submission(

        @Id
        @Column(name = "SUBMISSION_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        val remoteId: Int? = null,

        val contestId: Int? = null,

        @ManyToOne
        @JoinColumn(name = "PROBLEM_ID")
        var problem: Problem? = null,

        @ManyToOne(optional = false)
        @JoinColumn(name = "HANDLE_ID")
        val userhandle: UserHandle? = null,

        @Enumerated(EnumType.STRING)
        val verdict: Verdict? = null,

        val submitted: LocalDateTime? = null
) {

    override fun hashCode() = remoteId.hashCode()

    override fun equals(other: Any?): Boolean {
        return if (other is Submission) {
            other.remoteId == remoteId
        } else {
            false
        }
    }
}