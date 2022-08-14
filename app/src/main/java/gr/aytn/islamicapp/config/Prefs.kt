package gr.aytn.islamicapp.config

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){

    private var USER_ID = "USER_ID"
    private var FAJR = "FAJR"
    private var SUNRISE = "SUNRISE"
    private var DHUHR = "DHUHR"
    private var ASR = "ASR"
    private var MAGHRIB = "MAGHRIB"
    private var ISHA = "ISHA"
    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)


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