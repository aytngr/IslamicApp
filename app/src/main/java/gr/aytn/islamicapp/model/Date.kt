package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Date (

    @SerializedName("readable"  ) var readable  : String?    = null,
    @SerializedName("timestamp" ) var timestamp : String?    = null,
    @SerializedName("gregorian" ) var gregorian : Gregorian? = Gregorian(),
    @SerializedName("hijri"     ) var hijri     : Hijri?     = Hijri()

)