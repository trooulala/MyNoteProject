package com.example.mynotes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.adapters.NotesAdapter
import com.example.mynotes.database.NotesDatabase
import com.example.mynotes.entities.Note
import com.example.mynotes.ui.theme.MyNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
        const val REQUEST_CODE_ADD_NOTE = 1
    }


    private lateinit var notesRecyclerView: RecyclerView
    private val noteList = ArrayList<Note>()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addNoteMain: ImageView = findViewById(R.id.addNoteMain)
        addNoteMain.setOnClickListener {
            startActivityForResult(
                Intent(applicationContext, CreateNoteActivity::class.java),
                REQUEST_CODE_ADD_NOTE
            )
        }

        notesRecyclerView = findViewById(R.id.notesRecyclerView)
        notesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        notesAdapter = NotesAdapter(noteList)
        notesRecyclerView.adapter = notesAdapter

        getNotes()

    }

    private fun getNotes() {
        // Using Coroutines for background database operation
        lifecycleScope.launch {
            val notes = withContext(Dispatchers.IO) {
                NotesDatabase.getDatabase(applicationContext).noteDao().getAllNotes()
            }

            // Log the notes after fetching
            Log.d("MY_NOTES", notes.toString())

            // You can also update the UI or adapter here if needed
            withContext(Dispatchers.Main) {
                if (noteList.isEmpty()) {
                    noteList.addAll(notes)
                    notesAdapter.notifyDataSetChanged()
                } else if (notes.isNotEmpty()) {
                    noteList.add(0, notes[0])
                    notesAdapter.notifyItemInserted(0)
                }

                notesRecyclerView.smoothScrollToPosition(0)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNotesTheme {
        Greeting("Android")
    }
}