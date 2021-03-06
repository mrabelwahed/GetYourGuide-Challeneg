package com.challenge.data.exceptions

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnExpectedError : Failure()
}
