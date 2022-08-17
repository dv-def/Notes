package com.example.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.notes.R
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.domain.Note
import com.example.notes.ui.about.AboutFragment
import com.example.notes.ui.edit.EditNoteFragment
import com.example.notes.ui.list.NotesListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        setSupportActionBar(binding.appToolbar)

        if (savedInstanceState.isNull()) {
            openFragment(
                fragment = NotesListFragment.newInstance(),
                addToBackStack = false
            )
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_bottom_nav_main -> {
                    openFragment(
                        fragment = NotesListFragment.newInstance(),
                        addToBackStack = false
                    )
                    true
                }
                R.id.item_bottom_nav_about -> {
                    openFragment(
                        fragment = AboutFragment(),
                        addToBackStack = false
                    )
                    true
                }
                else -> false
            }
        }
    }

    fun openEditScreen(note: Note?) {
        openFragment(
            fragment = EditNoteFragment.newInstance(note),
            addToBackStack = true
        )
    }

    fun back() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}

private fun Bundle?.isNull(): Boolean {
    return this == null
}