package gr.aytn.islamicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName


data class Quran(

    @SerializedName("chapter") var chapter: Int? = null,
    @SerializedName("verse") var verse: Int? = null,
    @SerializedName("text") var text: String? = null

)