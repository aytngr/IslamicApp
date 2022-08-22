package gr.aytn.islamicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PrayerTime (
    @ColumnInfo(name = "date") var date: String? = null,
    @ColumnInfo(name = "fajr") var fajr: String? = null,
    @ColumnInfo(name = "sunrise") var sunrise: String? = null,
    @ColumnInfo(name = "dhuhr") var dhuhr: String? = null,
    @ColumnInfo(name = "asr") var asr: String? = null,
    @ColumnInfo(name = "maghrib") var maghrib: String? = null,
    @ColumnInfo(name = "isha") var isha: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
        )