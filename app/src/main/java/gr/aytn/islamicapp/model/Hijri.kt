package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Hijri (

    @SerializedName("date"        ) var date        : String?           = null,
    @SerializedName("format"      ) var format      : String?           = null,
    @SerializedName("day"         ) var day         : String?           = null,
    @SerializedName("weekdayHijri") var weekdayHijri : WeekdayHijri? = WeekdayHijri(),
    @SerializedName("monthHijri"  ) var monthHijri  : MonthHijri?  = MonthHijri(),
    @SerializedName("year"        ) var year        : String?           = null,
    @SerializedName("designationHijri") var designationHijri : DesignationHijri? = DesignationHijri(),
    @SerializedName("holidays"    ) var holidays    : ArrayList<String> = arrayListOf()

)