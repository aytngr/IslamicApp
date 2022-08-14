package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Method (

    @SerializedName("id"       ) var id       : Int?      = null,
    @SerializedName("name"     ) var name     : String?   = null,
    @SerializedName("params"   ) var params   : Params?   = Params(),
    @SerializedName("location" ) var location : Location? = Location()

)