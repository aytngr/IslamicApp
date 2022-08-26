package gr.aytn.islamicapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Ayat(
    @ColumnInfo(name = "chapter") var chapter: Int? = null,
    @ColumnInfo(name = "verse") var verse: Int? = null,
    @ColumnInfo(name = "arabic") var arabic: String? = null,
    @ColumnInfo(name = "text") var text: String? = null,
    @ColumnInfo(name = "text_alikhan_musayev") var text_alikhan_musayev: String? = null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)