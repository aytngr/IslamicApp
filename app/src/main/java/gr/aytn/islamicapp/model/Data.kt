package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Data (

    @SerializedName("timings" ) var timings : Timings? = Timings(),
    @SerializedName("date"    ) var date    : Date?    = Date(),
    @SerializedName("meta"    ) var meta    : Meta?    = Meta()

)