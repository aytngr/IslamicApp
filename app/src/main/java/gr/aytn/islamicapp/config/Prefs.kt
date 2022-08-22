package gr.aytn.islamicapp.config

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){
    private var CHAPTER_NO = "CHAPTER_NO"
    private var CHAPTER_VERSE_COUNT = "CHAPTER_VERSE_COUNT"

    private var FAJR = "FAJR"
    private var SUNRISE = "SUNRISE"
    private var DHUHR = "DHUHR"
    private var ASR = "ASR"
    private var MAGHRIB = "MAGHRIB"
    private var ISHA = "ISHA"

    private var WARNING_MESSAGE = "WARNING_MESSAGE"

    private var CURRENT_MONTH = "CURRENT_MONTH"

    private var SELECTED_LOCATION = "SELECTED_LOCATION"

    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)

    var current_month: Int
        get() = preferences.getInt(CURRENT_MONTH,-1)
        set(value) = preferences.edit().putInt(CURRENT_MONTH, value).apply()

    var selected_location: String
        get() = preferences.getString(SELECTED_LOCATION,"Baku").toString()
        set(value) = preferences.edit().putString(SELECTED_LOCATION, value).apply()

    var chapter_no: Int
        get() = preferences.getInt(CHAPTER_NO,-1)
        set(value) = preferences.edit().putInt(CHAPTER_NO, value).apply()

    var chapter_verse_count: Int
        get() = preferences.getInt(CHAPTER_VERSE_COUNT,-1)
        set(value) = preferences.edit().putInt(CHAPTER_VERSE_COUNT, value).apply()

    var warning_message: String
        get() = preferences.getString(WARNING_MESSAGE,"").toString()
        set(value) = preferences.edit().putString(WARNING_MESSAGE, value).apply()


    var fajr_time: String
        get() = preferences.getString(FAJR,"00:00").toString()
        set(value) = preferences.edit().putString(FAJR, value).apply()

    var sunrise_time: String
        get() = preferences.getString(SUNRISE,"00:00").toString()
        set(value) = preferences.edit().putString(SUNRISE, value).apply()

    var dhuhr_time: String
        get() = preferences.getString(DHUHR,"00:00").toString()
        set(value) = preferences.edit().putString(DHUHR, value).apply()

    var asr_time: String
        get() = preferences.getString(ASR,"00:00").toString()
        set(value) = preferences.edit().putString(ASR, value).apply()

    var maghrib_time: String
        get() = preferences.getString(MAGHRIB,"00:00").toString()
        set(value) = preferences.edit().putString(MAGHRIB, value).apply()

    var isha_time: String
        get() = preferences.getString(ISHA,"00:00").toString()
        set(value) = preferences.edit().putString(ISHA, value).apply()


}