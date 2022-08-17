package com.example.notes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.app
import com.example.notes.databinding.FragmentNotesListBinding
import com.example.notes.domain.Note
import com.example.notes.ui.MainActivity

class NotesListFragment : Fragment(), NotesListView {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter
    private lateinit var presenter: NotesListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = NotesListPresenter(app().repository)
        presenter.attach(this)

        binding.fab.setOnClickListener {
            (requireActivity() as MainActivity).openEditScreen(null)
        }

        adapter = NotesAdapter(
            onItemClick = { note ->
                (requireActivity() as MainActivity).openEditScreen(note)
            },
            onClickDelete = { note, pos ->
                presenter.onClickDelete(note, pos)
            }
        )

        binding.rvNotesList.apply {
            adapter = this@NotesListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        presenter.onGetNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.detach()
    }

    override fun showData(notesList: List<Note>) {
        adapter.updateData(notesList)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        binding.notesLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.notesLoading.visibility = View.GONE
    }

    override fun deleteNote(note: Note, pos: Int) {
        adapter.delete(note, pos)
    }

    companion object {
        fun newInstance() = NotesListFragment()
    }
}