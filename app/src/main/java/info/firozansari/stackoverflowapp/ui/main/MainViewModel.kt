package info.firozansari.stackoverflowapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.firozansari.stackoverflowapp.api.ApiStatus
import info.firozansari.stackoverflowapp.api.StackoverflowRepository
import info.firozansari.stackoverflowapp.api.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val stackoverflowRepository: StackoverflowRepository
) : ViewModel() {

    // The most recent API response
    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>>
        get() = _questions

    // Holds the selected question data
    private val _navigateToSelectedQuestion = MutableLiveData<Question>()
    val navigateToSelectedQuestion: LiveData<Question>
        get() = _navigateToSelectedQuestion

    private var viewModelJob = Job() // Coroutines Job

    // A coroutine scope for that new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main
    )

    init {
        getUnansweredQuestion()
    }

    private fun getUnansweredQuestion() {
        coroutineScope.launch {
            val fromDate: Date = Date.from(ZonedDateTime.now().minusMonths(1).toInstant())
            // Will get the topic/ tagged value and min votes dynamically in future
            var getQuestionDeferred =
                stackoverflowRepository.getQuestions(fromDate.time, 5, "android")
            try {
                _apiStatus.value = ApiStatus.LOADING
                var apiResult = getQuestionDeferred.await()
                _apiStatus.value = ApiStatus.DONE
                _questions.value = apiResult.questionsList!!
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                _questions.value = ArrayList()
            }
        }
    }

    fun displayQuestionDetails(question: Question) {
        _navigateToSelectedQuestion.value = question
    }

    fun displayQuestionDetailsComplete() {
        //_navigateToSelectedQuestion.value = Question()
    }

    // Cancel the Coroutines Job
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}