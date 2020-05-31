package com.compprogserver.consumer

import com.compprogserver.entity.Platform
import com.compprogserver.entity.UserHandle
import com.compprogserver.entity.problem.Submission
import org.json.JSONObject


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
            platform = Platform.CODEFORCES)
}

fun convertToSubmission(response: String) : Set<Submission> {
    val submissionSet = mutableSetOf<Submission>()
    val jsonBody = JSONObject(response)
    val submissions = jsonBody.getJSONArray("result")
    for(i in 0 until  submissions.length()){
        val submission = submissions.getJSONObject(i)

    }
    return submissionSet
}