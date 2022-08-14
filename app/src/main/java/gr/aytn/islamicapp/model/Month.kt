package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Month (

    @SerializedName("number" ) var number : Int?    = null,
    @SerializedName("en"     ) var en     : String? = null

)