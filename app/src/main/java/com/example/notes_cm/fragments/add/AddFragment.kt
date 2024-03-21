package com.example.notes_cm.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes_cm.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel


class AddFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        ViewModelProvider(this)[NoteViewModel::class.java].also { this.mNoteViewModel = it }

        val button = view.findViewById<Button>(R.id.save)
        button.setOnClickListener {
            addNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToList)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        return view
    }

    private fun addNote() {
        val noteText = view?.findViewById<EditText>(R.id.addNote)?.text.toString()
        val noteDescription = view?.findViewById<EditText>(R.id.addDescription)?.text.toString()

        if(noteText.isEmpty()) {
            Toast.makeText(view?.context, context?.getString(R.string.empty_note_alert), Toast.LENGTH_LONG).show()
        }
        else {
            val note = Note(0, noteText, noteDescription, "10/04/2024")

            mNoteViewModel.addNote(note)

            Toast.makeText(requireContext(), context?.getString(R.string.success_note_save_alert), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }
}