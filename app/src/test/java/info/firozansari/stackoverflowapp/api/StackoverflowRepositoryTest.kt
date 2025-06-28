package info.firozansari.stackoverflowapp.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import info.firozansari.stackoverflowapp.api.model.Owner
import info.firozansari.stackoverflowapp.api.model.Question
import info.firozansari.stackoverflowapp.api.model.StackoverflowResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.time.ZonedDateTime
import java.util.Date

@RunWith(JUnit4::class)
class StackoverflowRepositoryTest{

    @Rule
    @JvmField
    val instantRuleExecutor = InstantTaskExecutorRule()

    private val service = Mockito.mock(StackoverflowService::class.java)

    lateinit var stackoverflowRepository: StackoverflowRepository


    @Test
    fun getListQuestions() {

        stackoverflowRepository = StackoverflowRepository(service)

        val tags = listOf<String>("Android", "kotlin")
        val owner = Owner("userid", "image", "userName", "profileLinkurl")
        val question = Question(tags, owner, true, 1, 1, 1, 12345, 12345678, 12345678, "TestTitle", "http://firozansari.info")
        val listQuestions = listOf<Question>(question)
        val questionsResponse = StackoverflowResponse(listQuestions, true, 100, 1)
        val fromDate: Date = Date.from(ZonedDateTime.now().minusMonths(1).toInstant())

        `when`(service.getQuestions(fromDate.time, "desc", 5, "votes", "stackoverflow", "android" )).thenReturn(CompletableDeferred(questionsResponse))

        val result = runBlocking {
            stackoverflowRepository.getQuestions(fromDate.time,5, "android" ).await()
        }

        Assert.assertNotNull(result)
        Assert.assertThat(result.hasMore, IsEqual(true))

        val listQuestion = result.questionsList
        Assert.assertNotNull(listQuestion)
        Assert.assertThat(listQuestion?.size, IsEqual(1))
    }
}