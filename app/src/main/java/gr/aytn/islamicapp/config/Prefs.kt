package gr.aytn.islamicapp.config

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context){
    private var CHAPTER_NO = "CHAPTER_NO"
    private var CHAPTER_VERSE_COUNT = "CHAPTER_VERSE_COUNT"

    private var FAJR = "FAJR"
    private var SUNRISE = "SUNRISE"
    private var DUHA = "DUHA"
    private var DHUHR = "DHUHR"
    private var ASR = "ASR"
    private var MAGHRIB = "MAGHRIB"
    private var ISHA = "ISHA"

    private var WARNING_MESSAGE = "WARNING_MESSAGE"

    private var CURRENT_MONTH = "CURRENT_MONTH"

    private var SELECTED_LOCATION = "SELECTED_LOCATION"
    private var RANDOM_AYAH = "RANDOM_AYAH"
    private var THEME = "THEME"
    private var REMAINING_TIME = "REMAINING_TIME"
    private var SELECTED_TRANSLATION = "SELECTED_TRANSLATION"
    private var STICKY_NOTF = "STICKY_NOTF"
    private var ARABIC_FONT_SIZE = "ARABIC_FONT_SIZE"
    private var TRANSLATION_FONT_SIZE = "TRANSLATION_FONT_SIZE"
    private var SELECTED_TEXT_LANGUAGE = "SELECTED_TEXT_LANGUAGE"
    private var SETTINGS_FROM_CHAPTER = "SETTINGS_FROM_CHAPTER"
    private var SELECTED_ASR_CALCULATION = "SELECTED_ASR_CALCULATION"
    private var SELECTED_NOTF_STYLE = "SELECTED_NOTF_STYLE"
    private var SELECTED_NOTF_ICON = "SELECTED_NOTF_ICON"

    private var FAJR_MANUAL = "FAJR_MANUAL"
    private var DHUHR_MANUAL = "DHUHR_MANUAL"
    private var ASR_MANUAL = "ASR_MANUAL"
    private var MAGHRIB_MANUAL = "MAGHRIB_MANUAL"
    private var ISHA_MANUAL = "ISHA_MANUAL"

    private val preferences: SharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE)

    var fajr_manual: Int
        get() = preferences.getInt(FAJR_MANUAL,0)
        set(value) = preferences.edit().putInt(FAJR_MANUAL, value).apply()

    var dhuhr_manual: Int
        get() = preferences.getInt(DHUHR_MANUAL,0)
        set(value) = preferences.edit().putInt(DHUHR_MANUAL, value).apply()

    var asr_manual: Int
        get() = preferences.getInt(ASR_MANUAL,0)
        set(value) = preferences.edit().putInt(ASR_MANUAL, value).apply()

    var maghrib_manual: Int
        get() = preferences.getInt(MAGHRIB_MANUAL,0)
        set(value) = preferences.edit().putInt(MAGHRIB_MANUAL, value).apply()

    var isha_manual: Int
        get() = preferences.getInt(ISHA_MANUAL,0)
        set(value) = preferences.edit().putInt(ISHA_MANUAL, value).apply()

    var selected_notf_style: String
        get() = preferences.getString(SELECTED_NOTF_STYLE,"Stil 1").toString()
        set(value) = preferences.edit().putString(SELECTED_NOTF_STYLE, value).apply()

    var selected_notf_icon: String
        get() = preferences.getString(SELECTED_NOTF_ICON,"T??tbiq ni??an??").toString()
        set(value) = preferences.edit().putString(SELECTED_NOTF_ICON, value).apply()

    var selected_asr_calculation: String
        get() = preferences.getString(SELECTED_ASR_CALCULATION,"Standart (??afi, Maliki, H??nb??li)").toString()
        set(value) = preferences.edit().putString(SELECTED_ASR_CALCULATION, value).apply()

    var arabic_font_size: Int
        get() = preferences.getInt(ARABIC_FONT_SIZE,28)
        set(value) = preferences.edit().putInt(ARABIC_FONT_SIZE, value).apply()

    var translation_font_size: Int
        get() = preferences.getInt(TRANSLATION_FONT_SIZE,20)
        set(value) = preferences.edit().putInt(TRANSLATION_FONT_SIZE, value).apply()

    var selected_translation: String
        get() = preferences.getString(SELECTED_TRANSLATION,"??lixan Musayev").toString()
        set(value) = preferences.edit().putString(SELECTED_TRANSLATION, value).apply()

    var selected_text_language: String
        get() = preferences.getString(SELECTED_TEXT_LANGUAGE,"araz").toString()
        set(value) = preferences.edit().putString(SELECTED_TEXT_LANGUAGE, value).apply()


    var sticky_notf: Boolean
        get() = preferences.getBoolean(STICKY_NOTF,false)
        set(value) = preferences.edit().putBoolean(STICKY_NOTF, value).apply()

    var current_month: Int
        get() = preferences.getInt(CURRENT_MONTH,-1)
        set(value) = preferences.edit().putInt(CURRENT_MONTH, value).apply()

    var selected_location: String
        get() = preferences.getString(SELECTED_LOCATION,"Baku").toString()
        set(value) = preferences.edit().putString(SELECTED_LOCATION, value).apply()

    var theme: String
        get() = preferences.getString(THEME,"Light").toString()
        set(value) = preferences.edit().putString(THEME, value).apply()

    var random_ayah: String
        get() = preferences.getString(RANDOM_AYAH,"").toString()
        set(value) = preferences.edit().putString(RANDOM_AYAH, value).apply()

    var chapter_no: Int
        get() = preferences.getInt(CHAPTER_NO,0)
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

    var duha_time: String
        get() = preferences.getString(DUHA,"00:00").toString()
        set(value) = preferences.edit().putString(DUHA, value).apply()

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