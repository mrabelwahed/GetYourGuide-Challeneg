package com.challenge.domain.interactor

import com.challenge.domain.repository.ReviewRepository
import com.challenge.ui.dto.QueryDTO
import com.rules.RxSchedulerRule
import io.reactivex.Flowable
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnit

class GetReviewListUseCaseTest {

    @Rule
    @JvmField
    var mockitoRule = MockitoJUnit.rule()!!

    lateinit var getGifListUseCase: GetReviewListUseCase

    @Mock
    lateinit var reviewRepo: ReviewRepository

    @Rule
    @JvmField
    var testSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    val query = QueryDTO(0)

    @Before
    fun setup() {
        getGifListUseCase = GetReviewListUseCase(reviewRepo)
        given(reviewRepo.getReviewList(query)).willReturn(Flowable.just(emptyList()))
    }

    @Test
    fun `usecase is ready for test`() {
        assertNotNull(getGifListUseCase)
    }

    @Test
    fun `should get data from repository`() {
        getGifListUseCase.execute(query)
        verify(reviewRepo).getReviewList(query)
        verifyNoMoreInteractions(reviewRepo)
    }
}
