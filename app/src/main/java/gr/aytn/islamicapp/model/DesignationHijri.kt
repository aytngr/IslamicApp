package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class DesignationHijri (

    @SerializedName("abbreviated" ) var abbreviated : String? = null,
    @SerializedName("expanded"    ) var expanded    : String? = null

)