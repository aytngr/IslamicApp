package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Timings (

    @SerializedName("Fajr"       ) var Fajr       : String? = null,
    @SerializedName("Sunrise"    ) var Sunrise    : String? = null,
    @SerializedName("Dhuhr"      ) var Dhuhr      : String? = null,
    @SerializedName("Asr"        ) var Asr        : String? = null,
    @SerializedName("Sunset"     ) var Sunset     : String? = null,
    @SerializedName("Maghrib"    ) var Maghrib    : String? = null,
    @SerializedName("Isha"       ) var Isha       : String? = null,
    @SerializedName("Imsak"      ) var Imsak      : String? = null,
    @SerializedName("Midnight"   ) var Midnight   : String? = null,
    @SerializedName("Firstthird" ) var Firstthird : String? = null,
    @SerializedName("Lastthird"  ) var Lastthird  : String? = null

)