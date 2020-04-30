package com.challenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.data.exceptions.Failure
import com.challenge.domain.interactor.GetReviewListUseCase
import com.challenge.ui.dto.QueryDTO
import com.challenge.ui.mapper.ReviewModelMapper
import com.challenge.ui.viewstate.ServerDataState
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject


class ReviewListViewModel @Inject constructor(private val getGifListUseCase: GetReviewListUseCase) :
    BaseViewModel() {
    val viewState = MutableLiveData<ServerDataState>()
    val liveReviewData: LiveData<ServerDataState>
        get() = viewState

    private var lastOffset = 0L

    private fun setFailure(throwable: Throwable): ServerDataState {
        return when (throwable) {
            is UnknownHostException -> ServerDataState.Error(Failure.NetworkConnection)
            is HttpException -> ServerDataState.Error(Failure.ServerError)
            else -> ServerDataState.Error(Failure.UnExpectedError)
        }
    }


    fun loadNextPage() {
        val disposable =   getGifListUseCase.execute(QueryDTO(lastOffset))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { res -> viewState.value = ServerDataState.Success(ReviewModelMapper.transform(res)) },
                { error -> viewState.value = setFailure(error) }
            )
        compositeDisposable.add(disposable)
    }

    fun incrementOffset(){
        this.lastOffset += 20
    }



    fun setNewQuery(){
        this.lastOffset =0L
    }


}