package com.example.notes

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notes.ui.list.NotesListFragment
import com.example.notes.ui.list.NotesViewHolder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest {
    private lateinit var scenario: FragmentScenario<NotesListFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun test_scroll() {
        onView(withId(R.id.rv_notes_list))
            .perform(
                RecyclerViewActions.scrollTo<NotesViewHolder>(
                    hasDescendant(withText("test 10"))
                )
            )
    }

    @Test
    fun test_clickOnItem() {
        onView(withId(R.id.rv_notes_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<NotesViewHolder>(
                    0,
                    click()
                )
            )
    }
}