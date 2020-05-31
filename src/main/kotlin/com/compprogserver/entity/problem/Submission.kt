package com.compprogserver.entity.problem

import com.compprogserver.entity.UserHandle
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

        @ManyToOne(optional = false)
        @JoinColumn(name = "PROBLEM_ID")
        val problem : Problem? = null,

        @ManyToOne(optional = false)
        @JoinColumn(name = "HANDLE_ID")
        val userhandle : UserHandle? = null,

        @Enumerated(EnumType.STRING)
        val verdict: Verdict

)