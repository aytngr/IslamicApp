package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Location (

    @SerializedName("latitude"  ) var latitude  : Double? = null,
    @SerializedName("longitude" ) var longitude : Double? = null

)