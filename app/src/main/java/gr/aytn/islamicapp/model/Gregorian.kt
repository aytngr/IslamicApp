package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Gregorian (

    @SerializedName("date"        ) var date        : String?      = null,
    @SerializedName("format"      ) var format      : String?      = null,
    @SerializedName("day"         ) var day         : String?      = null,
    @SerializedName("weekday"     ) var weekday     : Weekday?     = Weekday(),
    @SerializedName("month"       ) var month       : Month?       = Month(),
    @SerializedName("year"        ) var year        : String?      = null,
    @SerializedName("designation" ) var designation : Designation? = Designation()

)