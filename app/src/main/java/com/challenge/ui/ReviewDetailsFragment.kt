package com.challenge.ui

import android.os.Bundle
import android.view.View
import com.challenge.AppConst.keys.REVIEW_ITEM
import com.challenge.R
import com.challenge.ui.model.ReviewModel
import kotlinx.android.synthetic.main.fragment_review_details.*
import kotlinx.android.synthetic.main.item_review.rb_rating
import kotlinx.android.synthetic.main.item_review.tv_author_name
import kotlinx.android.synthetic.main.item_review.tv_message

class ReviewDetailsFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gif = arguments?.getParcelable<ReviewModel>(REVIEW_ITEM)
        gif?.let { setData(it) }
    }

    private fun setData(it: ReviewModel) {
        tv_author_name.text = it.authorName
        rb_rating.rating = it.rating.toFloat()
        tv_message.text = it.message
        it.travelerType?.let { type -> tv_traveller_type.text = "Traveller Type : $type" }
        it.authorCountry?.let { type -> tv_country.text = "Country : $type" }
    }


    override fun getLayoutById() = R.layout.fragment_review_details

}