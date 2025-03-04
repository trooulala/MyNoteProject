package com.example.mynotes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "dateTime")
    var dateTime: String? = null,

    @ColumnInfo(name = "subtitle")
    var subtitle: String? = null,

    @ColumnInfo(name = "noteText")
    var noteText: String? = null,

    @ColumnInfo(name = "imagePath")
    var imagePath: String? = null,

    @ColumnInfo(name = "color")
    var color: String? = null,

    @ColumnInfo(name = "webLink")
    var webLink: String? = null


) : Serializable {

    override fun toString(): String {
        return "${title ?: "No Title"} : ${dateTime ?: "No Date"}"
    }

}
