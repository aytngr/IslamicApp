package gr.aytn.islamicapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.islamicapp.model.Ayat

@Dao
interface QuranDao {

    @Query("SELECT * FROM ayat WHERE chapter = :chapterNo ORDER BY verse ASC")
    fun searchByChapterNo(chapterNo: Int): LiveData<List<Ayat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAyat(ayat: Ayat)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAyat(ayat: Ayat)

    @Delete
    fun deleteAyat(ayat: Ayat)

}