package com.example.notes_cm.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes_")
class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: String
) : Parcelable