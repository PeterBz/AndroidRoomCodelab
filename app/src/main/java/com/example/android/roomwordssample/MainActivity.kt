package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    // DIES IST NEU: Der "Launcher", der auf das Ergebnis der Eingabe wartet
    private val newWordActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Daten holen
            result.data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                val word = Word(word = reply)
                wordViewModel.insert(word) // In Datenbank speichern
            }
        } else {
            Toast.makeText(this, "Kein Wort gespeichert (leer)", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val repository = (application as WordsApplication).repository
        val factory = WordViewModelFactory(repository)
        wordViewModel = ViewModelProvider(this, factory).get(WordViewModel::class.java)

        wordViewModel.allWords.observe(this) { words ->
            words.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // HIER STARTEN WIR JETZT DIE EINGABE-SEITE
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            newWordActivityLauncher.launch(intent)
        }
    }
}