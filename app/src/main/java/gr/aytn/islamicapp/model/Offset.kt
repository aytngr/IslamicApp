package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Offset (

    @SerializedName("Imsak"    ) var Imsak    : Int? = null,
    @SerializedName("Fajr"     ) var Fajr     : Int? = null,
    @SerializedName("Sunrise"  ) var Sunrise  : Int? = null,
    @SerializedName("Dhuhr"    ) var Dhuhr    : Int? = null,
    @SerializedName("Asr"      ) var Asr      : Int? = null,
    @SerializedName("Maghrib"  ) var Maghrib  : Int? = null,
    @SerializedName("Sunset"   ) var Sunset   : Int? = null,
    @SerializedName("Isha"     ) var Isha     : Int? = null,
    @SerializedName("Midnight" ) var Midnight : Int? = null

)