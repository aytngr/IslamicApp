package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Params (

    @SerializedName("Fajr" ) var Fajr : Int? = null,
    @SerializedName("Isha" ) var Isha : Int? = null

)