package com.challenge.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ReviewModel(
    val id: String,
    val authorName: String,
    val authorCountry: String?,
    val message: String ,
    val rating: Int,
    val travelerType: String?

) : Parcelable