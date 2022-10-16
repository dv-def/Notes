package com.example.notes.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.notes.app
import com.example.notes.databinding.FragmentEditNoteBinding
import com.example.notes.domain.Note
import com.example.notes.ui.MainActivity

class EditNoteFragment : Fragment(), EditNoteView {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: EditNotePresenter
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        note = arguments?.getParcelable(ARGUMENT_NOTE) as Note?
        note?.let {
            binding.editNoteTitle.setText(it.title)
            binding.editNoteDescription.setText(it.title)
        }

        presenter = EditNotePresenter(app().repository)
        presenter.attach(this)

        binding.btnSaveNote.setOnClickListener {
            if (note != null) {
                note = note!!.copy(
                    id = note!!.id,
                    title = binding.editNoteTitle.text.toString(),
                    description = binding.editNoteDescription.text.toString()
                )
                presenter.updateNote(note!!)
            } else {
                presenter.saveNote(getNote())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.detach()
    }

    override fun showTitleError(message: String) {
        binding.editNoteTitle.hint = message
    }

    override fun showDescriptionError(message: String) {
        binding.editNoteDescription.hint = message
    }

    override fun showDataError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun success() {
        Toast.makeText(requireContext(), "Заметка успешно сохранена", Toast.LENGTH_SHORT).show()
        (requireActivity() as MainActivity).back()
    }

    private fun getNote(): Note {
        return Note(
            title = binding.editNoteTitle.text.toString(),
            description = binding.editNoteDescription.text.toString()
        )
    }

    companion object {
        private const val ARGUMENT_NOTE = "ARGUMENT_NOTE"

        fun newInstance(note: Note?): EditNoteFragment {
            val args = Bundle().apply {
                putParcelable(ARGUMENT_NOTE, note)
            }

            return EditNoteFragment().apply {
                arguments = args
            }
        }
    }
}