package com.challenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.challenge.R
import com.challenge.ui.model.ReviewModel
import kotlinx.android.synthetic.main.item_review.view.*
import javax.inject.Inject


class ReviewListAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val reviews = ArrayList<ReviewModel>()
    private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, null)
        return ReviewViewHolder(view)
    }

    override fun getItemCount() = reviews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReviewViewHolder) {
            holder.authorName.text = reviews[position].authorName
            holder.ratingBar.rating = reviews[position].rating.toFloat()
            holder.message.text = reviews[position].message
            holder.itemView.setOnClickListener {
                listener.onClick(position, it)
            }
        }

    }


    //main item
    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val authorName: TextView = view.tv_author_name
        val message: TextView = view.tv_message
        val ratingBar: RatingBar = view.rb_rating
    }


    fun addReviews(list: ArrayList<ReviewModel>) {
        reviews.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAll() {
        reviews.clear()
        notifyDataSetChanged()
    }

    fun setClickListener(listener: OnClickListener) {
        this.listener = listener
    }

}


