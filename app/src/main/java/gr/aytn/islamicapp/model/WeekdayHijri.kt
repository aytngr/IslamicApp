package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class WeekdayHijri (

    @SerializedName("en" ) var en : String? = null,
    @SerializedName("ar" ) var ar : String? = null

)