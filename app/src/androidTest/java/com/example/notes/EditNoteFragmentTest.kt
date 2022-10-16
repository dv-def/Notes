package com.example.notes

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.notes.domain.Note
import com.example.notes.ui.edit.EditNoteFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditNoteFragmentTest {
    private lateinit var scenario: FragmentScenario<EditNoteFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_testBundle() {
        val note = Note(
            title = "Test note",
            description = "Test description"
        )

        val args = bundleOf("ARGUMENT_NOTE" to note)
        val scenario = launchFragmentInContainer<EditNoteFragment>(args, R.style.ThemeOverlay_AppCompat)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val assertion = matches(withText("Test note"))
        onView(withId(R.id.edit_note_title)).check(assertion)
    }

    @Test
    fun fragment_testSetError() {
        scenario.onFragment { fragment ->
            fragment.showTitleError("Заполните заголовок")
        }

        val assertion = matches(withHint("Заполните заголовок"))
        onView(withId(R.id.edit_note_title)).check(assertion)
    }
}