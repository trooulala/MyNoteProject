package com.example.mynotes.adapters

import com.example.mynotes.entities.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R

class NotesAdapter(
    private val notes: List<Note>,
//    private val notesListener: NotesListener
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.setNote(notes[position])
//        holder.itemView.setOnClickListener {
//            notesListener.onNoteClicked(notes[position], position)
//        }
    }

    override fun getItemCount(): Int = notes.size

    override fun getItemViewType(position: Int): Int = position

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textSubtitle: TextView = itemView.findViewById(R.id.textSubtitle)
        private val textDateTime: TextView = itemView.findViewById(R.id.textDateTime)

        fun setNote(note: Note) {
            textTitle.text = note.title

            if (note.subtitle.isNullOrBlank()) {
                textSubtitle.visibility = View.GONE
            } else {
                textSubtitle.text = note.subtitle
                textSubtitle.visibility = View.VISIBLE
            }

            textDateTime.text = note.dateTime
        }
    }
}