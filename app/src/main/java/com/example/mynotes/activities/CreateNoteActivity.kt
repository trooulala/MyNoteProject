package com.example.mynotes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.R
import com.example.mynotes.database.NotesDatabase
import com.example.mynotes.entities.Note
import com.example.mynotes.ui.theme.MyNotesTheme
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat;
import java.util.*

class CreateNoteActivity : ComponentActivity() {

    private lateinit var inputNoteTitle: EditText
    private lateinit var inputNoteSubtitle: EditText
    private lateinit var inputNoteText: EditText
    private lateinit var textDateTime: TextView

    private lateinit var viewSubtitleIndicator : View

    private lateinit var selectedNoteColor : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val imageBack: ImageView = findViewById(R.id.iconBack)
        imageBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val TAG = CreateNoteActivity::class.java.simpleName

        inputNoteTitle = findViewById(R.id.inputNoteTitle)
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle)
        inputNoteText = findViewById(R.id.inputNote)
        textDateTime = findViewById(R.id.textDateTime)
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndocator)

        // Set current date and time
        val currentDateTime = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
            .format(Date())

        textDateTime.text = currentDateTime

        Log.d(TAG, "CreateNoteActivity initialized with date: $currentDateTime")

        val imageSave = findViewById<ImageView>(R.id.iconSave)
        imageSave.setOnClickListener {
            saveNote()
        }

        selectedNoteColor = "#333333"

        initMiscellaneous()
    }

    private fun saveNote() {
        val noteTitle = inputNoteTitle.text.toString().trim()
        val noteSubtitle = inputNoteSubtitle.text.toString().trim()
        val noteText = inputNoteText.text.toString().trim()

        if (noteTitle.isEmpty()) {
            Toast.makeText(this, "Note title can't be empty!", Toast.LENGTH_SHORT).show()
            return
        } else if (noteSubtitle.isEmpty() && noteText.isEmpty()) {
            Toast.makeText(this, "Note can't be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val note = Note(
            title = noteTitle,
            subtitle = noteSubtitle,
            noteText = noteText,
            dateTime = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(Date())
        )

        // Using Kotlin Coroutines for background DB operations
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                NotesDatabase.getDatabase(applicationContext).noteDao().insertNote(note)
            }
            // After saving the note, return to the previous screen
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initMiscellaneous() {
        val layoutMiscellaneous = findViewById<LinearLayout>(R.id.layoutMiscellaneous)
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous)

        layoutMiscellaneous.findViewById<View>(R.id.textMiscellaneous).setOnClickListener {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    BottomSheetBehavior.STATE_EXPANDED
                } else {
                    BottomSheetBehavior.STATE_COLLAPSED
                }
        }
    }


}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyNotesTheme {
        Greeting2("Android")
    }
}