package com.flowz.sixtjobapp2.ui.cars_list

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowz.agromailjobtask.adapter.CarsAdapter
import com.flowz.sixtjobapp2.MainActivity
import com.flowz.sixtjobapp2.R
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class CarsListFragmentTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    val LISTITEM = 5

    @Test
    fun testingViews() {

        onView(withId(R.id.welcome_text_marquee)).check(matches(isDisplayed()))

        onView(withId(R.id.open_map_view)).check(matches(isDisplayed()))

        onView(withId(R.id.welcome_text_marquee)).check(matches(withText(R.string.welcome_text)))

        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_onCarClicked_isCarDetailFragmentVisible() {
        onView(withId(R.id.rv_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CarsAdapter.ImageViewHolder>(
                    LISTITEM,
                    ViewActions.click()
                )
            )

        onView(withId(R.id.dlongitude)).check(matches(isDisplayed()))
        onView(withId(R.id.dlatitude)).check(matches(isDisplayed()))
    }

    @Test
    fun test_onOpenMapViewButtonClicked_isMapViewFragmentVisible() {
        onView(withId(R.id.open_map_view))
            .perform(ViewActions.click())

        onView(withId(R.id.google_map)).check(matches(isDisplayed()))

    }

    @Test
    fun test_backNavigation_toCarsListFragment() {
        onView(withId(R.id.rv_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CarsAdapter.ImageViewHolder>(
                    LISTITEM,
                    ViewActions.click()
                )
            )

        onView(withId(R.id.dlatitude)).check(matches(isDisplayed()))

        Espresso.pressBack()

        onView(withId(R.id.rv_list)).check(matches(isDisplayed()))
    }

}