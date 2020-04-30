package com.challenge.data.repository

import com.challenge.data.network.apis.ReviewApi
import com.challenge.data.network.response.ReviewsResponse
import com.rules.RxSchedulerRule
import com.squareup.okhttp.mockwebserver.MockResponse
import com.util.BaseNetwrokTest
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class ReviewDataRepositoryTest : BaseNetwrokTest() {

    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    lateinit var repository: ReviewDataRepository
    lateinit var reviewApi: ReviewApi

    val testSubscriber = TestSubscriber<ReviewsResponse>()

    @Before
    override fun setup() {
        super.setup()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockServer.url("/").toString())
            .build()
        reviewApi = retrofit.create(ReviewApi::class.java)
        repository = ReviewDataRepository(reviewApi)
    }

    override fun isMockServerEnable() = true


    @Test
    fun `repository is ready for test`() {
        assertNotNull(repository)
    }


    @Test
    fun ` search gif api should return list of 20 gifs per page `() {
        mockHttpresponse("get_reviews.json", HttpURLConnection.HTTP_OK)
        reviewApi.getReviews( 20, 0).subscribe(testSubscriber)
        testSubscriber.assertNoErrors().assertComplete()
        val response = testSubscriber.values()[0]
        val returnedSize = response.reviews.size
        assertEquals("reviews count should be only 20 in this test case", returnedSize, 20)
    }

    @Test
    fun `search gif api should return server error when there is internal server error`() {
        this.mockHttpResponse(MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR))
        reviewApi.getReviews(20, 0).subscribe(testSubscriber)
        testSubscriber.assertNoValues().assertNotComplete()
        assertEquals(1, testSubscriber.errorCount())
    }
}