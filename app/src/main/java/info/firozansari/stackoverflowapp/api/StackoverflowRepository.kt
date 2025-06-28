package info.firozansari.stackoverflowapp.api

import info.firozansari.stackoverflowapp.api.model.StackoverflowResponse
import javax.inject.Inject
import kotlinx.coroutines.Deferred

class StackoverflowRepository @Inject constructor
    (private val stackoverflowService: StackoverflowService) {

    fun getQuestions(
        formDate: Long,
        minVotes: Int,
        tagged: String
    ): Deferred<StackoverflowResponse> =
        stackoverflowService.getQuestions(
            formDate,
            "desc",
            minVotes,
            "votes",
            "stackoverflow",
            tagged
        )
}