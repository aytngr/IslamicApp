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

    private var FAJR_TOMORROW = "FAJR_TOMORROW"
    private var SUNRISE_TOMORROW = "SUNRISE_TOMORROW"
    private var DHUHR_TOMORROW = "DHUHR_TOMORROW"
    private var ASR_TOMORROW = "ASR_TOMORROW"
    private var MAGHRIB_TOMORROW = "MAGHRIB_TOMORROW"
    private var ISHA_TOMORROW = "ISHA_TOMORROW"

    private var FAJR_YESTERDAY = "FAJR_YESTERDAY"
    private var SUNRISE_YESTERDAY = "SUNRISE_YESTERDAY"
    private var DHUHR_YESTERDAY = "DHUHR_YESTERDAY"
    private var ASR_YESTERDAY = "ASR_YESTERDAY"
    private var MAGHRIB_YESTERDAY = "MAGHRIB_YESTERDAY"
    private var ISHA_YESTERDAY = "ISHA_YESTERDAY"
    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)

    var chapter_no: Int
        get() = preferences.getInt(CHAPTER_NO,-1)
        set(value) = preferences.edit().putInt(CHAPTER_NO, value).apply()

    var chapter_verse_count: Int
        get() = preferences.getInt(CHAPTER_VERSE_COUNT,-1)
        set(value) = preferences.edit().putInt(CHAPTER_VERSE_COUNT, value).apply()

    var warning_message: String
        get() = preferences.getString(WARNING_MESSAGE,"").toString()
        set(value) = preferences.edit().putString(WARNING_MESSAGE, value).apply()

    var fajr_time_yesterday: String
        get() = preferences.getString(FAJR_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(FAJR_YESTERDAY, value).apply()

    var sunrise_time_yesterday: String
        get() = preferences.getString(SUNRISE_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(SUNRISE_YESTERDAY, value).apply()

    var dhuhr_time_yesterday: String
        get() = preferences.getString(DHUHR_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(DHUHR_YESTERDAY, value).apply()

    var asr_time_yesterday: String
        get() = preferences.getString(ASR_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(ASR_YESTERDAY, value).apply()

    var maghrib_time_yesterday: String
        get() = preferences.getString(MAGHRIB_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(MAGHRIB_YESTERDAY, value).apply()

    var isha_time_yesterday: String
        get() = preferences.getString(ISHA_YESTERDAY,"00:00").toString()
        set(value) = preferences.edit().putString(ISHA_YESTERDAY, value).apply()

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

    var fajr_time_tomorrow: String
        get() = preferences.getString(FAJR_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(FAJR_TOMORROW, value).apply()

    var sunrise_time_tomorrow: String
        get() = preferences.getString(SUNRISE_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(SUNRISE_TOMORROW, value).apply()

    var dhuhr_time_tomorrow: String
        get() = preferences.getString(DHUHR_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(DHUHR_TOMORROW, value).apply()

    var asr_time_tomorrow: String
        get() = preferences.getString(ASR_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(ASR_TOMORROW, value).apply()

    var maghrib_time_tomorrow: String
        get() = preferences.getString(MAGHRIB_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(MAGHRIB_TOMORROW, value).apply()

    var isha_time_tomorrow: String
        get() = preferences.getString(ISHA_TOMORROW,"00:00").toString()
        set(value) = preferences.edit().putString(ISHA_TOMORROW, value).apply()
}