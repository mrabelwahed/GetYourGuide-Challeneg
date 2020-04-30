package com.challenge.ui

import android.widget.EditText
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challenge.*
import com.challenge.viewassertion.RecyclerViewItemCountAssertion
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ReviewListFragmentTest {

    private val mockWebServer = MockWebServer()
    private val portNumber = 8080

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun should_show_reviews_when_request_is_fired() {
        onView(withId(R.id.reviewList))
            .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun should_show_20_reviews_per_request() {
        onView(withId(R.id.reviewList))
            .check(RecyclerViewItemCountAssertion.hasItemCount(20))
        onView(withId(R.id.reviewList))
            .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun should_open_review_details_when_click_on_list_item() {

        onView(withId(R.id.reviewList))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.reviewList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ReviewListAdapter.ReviewViewHolder>(
                    5,
                    click()
                )
            )
 }


    fun getJson(path: String): String {
        val context = InstrumentationRegistry.getContext()
        val stream = context.resources.assets.open(path)
        return Utils.readTextStream(stream)
    }

}