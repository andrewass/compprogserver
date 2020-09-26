package com.compprogserver.entity.problem

import com.compprogserver.entity.UserHandle
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

        @ManyToOne
        @JoinColumn(name = "PROBLEM_ID")
        var problem: Problem,

        @ManyToOne
        @JoinColumn(name = "HANDLE_ID")
        val userHandle: UserHandle,

        @Enumerated(EnumType.STRING)
        val verdict: Verdict,

        val submitted: LocalDateTime
) {

    override fun hashCode() = userHandle.hashCode() + submitted.hashCode()

    override fun equals(other: Any?): Boolean {
        return if (other is Submission) {
            other.userHandle == userHandle && other.submitted == submitted
        } else {
            false
        }
    }
}