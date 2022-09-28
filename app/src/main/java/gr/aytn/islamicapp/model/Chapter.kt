package gr.aytn.islamicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chapter (
    @ColumnInfo(name = "number") var number: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "classification") var classification: String? = null,
    @ColumnInfo(name = "verses") var verses: Int? = null,
    @ColumnInfo(name = "favorite") var favorite: Boolean? = false,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
        )