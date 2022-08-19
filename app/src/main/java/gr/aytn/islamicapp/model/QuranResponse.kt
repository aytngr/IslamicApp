package gr.aytn.islamicapp.model

import com.google.gson.annotations.SerializedName


data class QuranResponse (

    @SerializedName("quran" ) var quran : ArrayList<Quran> = arrayListOf()

)