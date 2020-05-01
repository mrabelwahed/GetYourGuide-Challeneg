package com.challenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.challenge.data.exceptions.Failure
import com.challenge.domain.interactor.GetReviewListUseCase
import com.challenge.domain.model.Review
import com.challenge.domain.repository.ReviewRepository
import com.challenge.ui.dto.QueryDTO
import com.challenge.ui.viewstate.ServerDataState
import com.rules.RxSchedulerRule
import com.util.mock
import io.reactivex.Flowable
import java.net.UnknownHostException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit

class ReviewListViewModelTest {
    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()!!

    private lateinit var gifListViewModel: ReviewListViewModel

    lateinit var reviewListUseCase: GetReviewListUseCase

    @Mock
    var observer: Observer<ServerDataState> = mock()

    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var reviewRepository: ReviewRepository

    @Before
    fun setup() {
        reviewListUseCase = GetReviewListUseCase(reviewRepository)
        gifListViewModel = ReviewListViewModel(reviewListUseCase)
        gifListViewModel.liveReviewData.observeForever(observer)
    }

    @Test
    fun `view model is ready for test`() {
        assertNotNull(gifListViewModel)
    }

    @Test
    fun `should first page of gifs are loaded successfully`() {
        // Given
        val dummyQuery = QueryDTO(0)
        `when`(reviewRepository.getReviewList(dummyQuery)).thenReturn(Flowable.just(createGifList()))
        // when
        reviewRepository.getReviewList(dummyQuery)
        // then
        verify(reviewRepository).getReviewList(dummyQuery)
        verifyNoMoreInteractions(reviewRepository)
    }

    @Test
    fun `search gif should return lis of gifs`() {
        // given
        val dummyQuery = QueryDTO(0)
        `when`(reviewRepository.getReviewList(dummyQuery)).thenReturn(Flowable.just(createGifList()))
        // when
        reviewRepository.getReviewList(dummyQuery)
        gifListViewModel.viewState.value = ServerDataState.Success(Flowable.just(createGifList()))
        // then
        val captor = ArgumentCaptor.forClass(ServerDataState::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(gifListViewModel.viewState.value, value)
        }
    }

    @Test
    fun `no internet error case`() {
        // given
        val dummyQuery = QueryDTO(0)
        `when`(reviewRepository.getReviewList(dummyQuery))
            .thenReturn(Flowable.error(UnknownHostException()))
        // when
        reviewRepository.getReviewList(dummyQuery)
        gifListViewModel.viewState.value = ServerDataState.Error(Failure.NetworkConnection)
        // then
        val captor = ArgumentCaptor.forClass(ServerDataState::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(value, ServerDataState.Error(Failure.NetworkConnection))
        }
    }

//

    private fun createGifList(): List<Review> {
        val modelList = mutableListOf<Review>()
        for (x in 0..10) {
            modelList.add(createDummyGif(x))
        }
        return modelList
    }

    private fun createDummyGif(x: Int): Review {
        return Review("$x", "t$x", "country$x", "message$x", x, "$x")
    }
}
