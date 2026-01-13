package com.example.android.roomwordssample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Diese Annotation sagt Room, dass dies eine Datenbank-Tabelle ist
@Entity(tableName = "word_table")
class Word(
    // Hier definieren wir die Spalte "word" als Primärschlüssel
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)