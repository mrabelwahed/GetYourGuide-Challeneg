package com.challenge.domain.model


data class Review(
    val id: String,
    val authorName: String,
    val authorCountry: String?,
    val message: String,
    val rating: Int,
    val travelerType: String?
)