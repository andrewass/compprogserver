package com.compprogserver.consumer

import com.compprogserver.entity.Contest
import com.compprogserver.entity.Platform.CODEFORCES
import com.compprogserver.entity.User
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Problem
import com.compprogserver.entity.problem.ProblemTag
import com.compprogserver.entity.problem.Submission
import com.compprogserver.entity.problem.Verdict
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun convertToUserHandleCF(response: String, user: User): UserHandle? {
    val jsonBody = JSONObject(response)
    val handles = jsonBody.getJSONArray("result")
    if (handles.isEmpty) {
        return null
    }
    val handle = handles.getJSONObject(0)

    return UserHandle(
            userHandle = handle.getString("handle"),
            rating = handle.getInt("rating"),
            maxRating = handle.getInt("maxRating"),
            rank = handle.getString("rank"),
            maxRank = handle.getString("maxRank"),
            platform = CODEFORCES,
            user = user
    )
}

fun convertToSubmissions(response: String, userHandle: UserHandle): Set<Submission> {
    val submissionSet = mutableSetOf<Submission>()
    val jsonBody = JSONObject(response)
    val submissions = jsonBody.getJSONArray("result")

    for (i in 0 until submissions.length()) {
        val submission = submissions.getJSONObject(i)
        if (submission.getString("verdict") == "OK") {
            submissionSet.add(Submission(
                    remoteId = submission.getInt("id"),
                    userHandle = userHandle,
                    submitted = toLocateDateTime(submission.getLong("creationTimeSeconds")),
                    problem = convertToProblem(submission.getJSONObject("problem")),
                    verdict = Verdict.SOLVED
            ))
        }
    }
    return submissionSet
}

fun convertToContests(response: String): Set<Contest> {
    val contestSet = mutableSetOf<Contest>()
    val jsonBody = JSONObject(response)
    val contests = jsonBody.getJSONArray("result")

    for (i in 0 until contests.length()) {
        val contest = contests.getJSONObject(i)
        contestSet.add(Contest(
                contestName = contest.getString("name"),
                startTime = toLocateDateTime(contest.getLong("startTimeSeconds")),
                duration = contest.getInt("durationSeconds") / 60,
                platform = CODEFORCES,
                remoteId = contest.getInt("id")
        ))
    }
    return contestSet
}


private fun convertToProblem(problem: JSONObject): Problem {
    return Problem(
            platform = CODEFORCES,
            problemName = problem.getString("name"),
            problemUrl = "https://codeforces.com/contest/${problem.getInt("contestId")}/problem/${problem.getString("index")}",
            problemTags = toProblemTagList(problem.getJSONArray("tags"))
    )
}

private fun toProblemTagList(tags : JSONArray) : List<ProblemTag> {
    val problemTags = mutableListOf<ProblemTag>()
    for (i in 0 until tags.length()) {
        val problemTagDecode = tags.getString(i)
        val problemTag = ProblemTag.fromDecode(problemTagDecode)
        if(problemTag != null){
            problemTags.add(problemTag)
        }
    }
    return problemTags
}

private fun toLocateDateTime(unixTime: Long): LocalDateTime =
        Instant.ofEpochSecond(unixTime).atZone(ZoneOffset.UTC).toLocalDateTime()
