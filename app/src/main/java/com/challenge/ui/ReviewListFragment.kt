package com.challenge.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.AppConst.keys.REVIEW_ITEM
import com.challenge.BaseApp
import com.challenge.R
import com.challenge.data.exceptions.Failure
import com.challenge.ui.model.ReviewModel
import com.challenge.ui.viewmodel.ReviewListViewModel
import com.challenge.ui.viewmodel.ViewModelFactory
import com.challenge.ui.viewstate.ServerDataState
import kotlinx.android.synthetic.main.content_empty.*
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.fragment_review_list.*
import javax.inject.Inject


class ReviewListFragment : BaseFragment(), OnClickListener {
    private val reviewDetailsFragment = ReviewDetailsFragment()
    private lateinit var reviewListViewModel: ReviewListViewModel
    private var totalItemCount = 0
    private var lastVisibleItem = 0
    private var loading = false
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val VISIBLE_THRESHOLD = 1
    @Inject
    lateinit var reviewListAdapter: ReviewListAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    //private lateinit var searchView: SearchView

    private var newQueryIsFired = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as BaseApp).appComponent
            .newReviewLisComponent().inject(this)
        reviewListViewModel =
            ViewModelProvider(this, viewModelFactory)[ReviewListViewModel::class.java]
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        reviewListViewModel.loadNextPage()
    }


    private fun setupView() {
        linearLayoutManager = LinearLayoutManager(context)
        reviewListAdapter.setClickListener(this)
        reviewList.apply {
            layoutManager = linearLayoutManager
            adapter = reviewListAdapter
        }


    }

    private fun setData(response: ArrayList<ReviewModel>) {
        reviewListAdapter.addReviews(response)
    }


    override fun onClick(position: Int, view: View) {

        showReviewView(reviewListAdapter.reviews[position])
    }

    private fun observeReviewList() {
        reviewListViewModel.liveReviewData.observe(this, Observer {
            when (it) {
                is ServerDataState.Success<*> -> {
                    val gifItems = (it.item as ArrayList<ReviewModel>)
                    handleUISuccess()
                    setData(gifItems)
                }
                is ServerDataState.Error -> {
                    handleUIError()
                    setError(it.failure)
                }

                is ServerDataState.Loading -> {
                    handleUILoading()
                }
            }

        })
    }


    private fun handleUILoading() {
        loading = true
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }


    private fun handleUIError() {
        loading = false
        newQueryIsFired = false
        errorView.visibility = View.VISIBLE
        reviewList.visibility = View.GONE
        emptyView.visibility = View.GONE
    }


    private fun handleUISuccess() {
        loading = false
        newQueryIsFired = false
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        reviewList.visibility = View.VISIBLE
    }


    private fun setError(failure: Failure?) {
        failure?.let {
            when (it) {
                is Failure.NetworkConnection -> {
                    errorText.text = getString(R.string.failure_network_connection)
                }
                is Failure.ServerError -> {
                    errorText.text = getString(R.string.failure_server_error)
                    errorImage.setImageResource(R.drawable.undraw_page_not_found_su7k)

                }
                is Failure.UnExpectedError -> {
                    errorText.text = getString(R.string.failure_unexpected_error)
                    errorImage.setImageResource(R.drawable.undraw_page_not_found_su7k)
                }
            }
        }
    }


    override fun getLayoutById(): Int {
        return R.layout.fragment_review_list
    }

    private fun initUI() {
        setupView()
        setupLoadMoreListener()
        observeReviewList()
    }

    private fun setupLoadMoreListener() {
        reviewList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    if (!newQueryIsFired) {
                        reviewListViewModel.incrementOffset()
                        reviewListViewModel.loadNextPage()
                    }

                    loading = true
                }
            }
        })

    }

    private fun showReviewView(review: ReviewModel) {
        val bundle = Bundle()
        bundle.putParcelable(REVIEW_ITEM, review)
        reviewDetailsFragment.arguments = bundle

        (activity as BaseActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.container, reviewDetailsFragment)
            .addToBackStack(null)
            .commit()

    }

}
