package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class Meta (

    @SerializedName("latitude"                 ) var latitude                 : Double? = null,
    @SerializedName("longitude"                ) var longitude                : Double? = null,
    @SerializedName("timezone"                 ) var timezone                 : String? = null,
    @SerializedName("method"                   ) var method                   : Method? = Method(),
    @SerializedName("latitudeAdjustmentMethod" ) var latitudeAdjustmentMethod : String? = null,
    @SerializedName("midnightMode"             ) var midnightMode             : String? = null,
    @SerializedName("school"                   ) var school                   : String? = null,
    @SerializedName("offset"                   ) var offset                   : Offset? = Offset()

)