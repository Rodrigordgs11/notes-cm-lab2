package com.example.notes_cm.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notes_cm.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_cm.data.entities.Note
import com.example.notes_cm.data.vm.NoteViewModel
import android.widget.Toast.makeText
import com.example.notes_cm.data.converters.ConvertDate
import java.util.Date

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        view.findViewById<TextView>(R.id.updateNote).text = args.currentNote.note
        view.findViewById<TextView>(R.id.updateDescription).text = args.currentNote.description

        val updateButton = view.findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            updateNote()
        }

        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            deleteNote()
        }

        val backButton = view.findViewById<Button>(R.id.backToListFromUpdate)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return  view
    }

    private  fun updateNote(){
        val noteText = view?.findViewById<EditText>(R.id.updateNote)?.text.toString()
        val noteDescription = view?.findViewById<EditText>(R.id.updateDescription)?.text.toString()

        if(noteText.isEmpty()) {
            makeText(context , context?.getString(R.string.empty_note_alert), Toast.LENGTH_LONG).show()
        } else if (noteDescription.isEmpty() || noteDescription.length < 5){
            makeText(view?.context, context?.getString(R.string.empty_noteDescription_alert), Toast.LENGTH_LONG).show()
        } else {
            val date = Date()
            val formattedDate = ConvertDate.formatDate(date)
            val note = Note(args.currentNote.id, noteText, noteDescription, formattedDate)

            mNoteViewModel.updateNote(note)

            makeText(requireContext(), context?.getString(R.string.success_update_note_alert), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
            mNoteViewModel.deleteNote(args.currentNote)
            makeText(
                requireContext(),
                context?.getString(R.string.success_note_delete_alert),
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(context?.getString(R.string.no)) { _, _ -> }
        builder.setTitle(context?.getString(R.string.delete_confirmation))
        builder.setMessage(context?.getString(R.string.do_you_want_delete_button))
        builder.create().show()
    }
}