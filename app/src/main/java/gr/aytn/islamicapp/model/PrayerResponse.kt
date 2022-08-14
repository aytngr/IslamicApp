package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class PrayerResponse (

    @SerializedName("code"   ) var code   : Int?            = null,
    @SerializedName("status" ) var status : String?         = null,
    @SerializedName("data"   ) var data   : ArrayList<Data> = arrayListOf()

)