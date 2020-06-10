package com.compprogserver.consumer

import com.compprogserver.entity.Platform.CODEFORCES
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.Submission
import com.compprogserver.entity.problem.Verdict
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun convertToUserHandle(response: String): UserHandle? {
    val jsonBody = JSONObject(response)
    val handles = jsonBody.getJSONArray("result")
    if (handles.isEmpty) {
        return null
    }
    val handle = handles.getJSONObject(0)

    return UserHandle(
            username = handle.getString("handle"),
            rating = handle.getInt("rating"),
            maxRating = handle.getInt("maxRating"),
            rank = handle.getString("rank"),
            maxRank = handle.getString("maxRank"),
            platform = CODEFORCES)
}

fun convertToSubmission(response: String, userHandle: UserHandle): Set<Submission> {
    val submissionSet = mutableSetOf<Submission>()
    val jsonBody = JSONObject(response)
    val submissions = jsonBody.getJSONArray("result")
    for (i in 0 until submissions.length()) {
        val submission = submissions.getJSONObject(i)
        if (submission.getString("verdict") == "OK") {
            submissionSet.add(Submission(
                    remoteId = submission.getInt("id"),
                    userhandle = userHandle,
                    contestId = submission.getInt("contestId"),
                    submitted = toLocateDateTime(submission.getLong("creationTimeSeconds")),
                    problem = convertToProblem(submission.getJSONObject("problem")),
                    verdict = Verdict.SOLVED
            ))
        }
    }
    return submissionSet
}

fun convertToProblem(problem: JSONObject): Problem {
    return Problem(
            platform = CODEFORCES,
            problemName = problem.getString("name"),
            problemUrl = "https://codeforces.com/contest/${problem.getInt("contestId")}/problem/${problem.getString("index")}"
    )
}

private fun toLocateDateTime(unixTime: Long): LocalDateTime =
        Instant.ofEpochSecond(unixTime).atZone(ZoneOffset.UTC).toLocalDateTime()
