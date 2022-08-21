package gr.aytn.islamicapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter

@Dao
interface QuranDao {

    @Query("SELECT * FROM ayat")
    fun getAllQuran(): LiveData<List<Ayat>>

    @Query("SELECT * FROM chapter")
    fun getAllChapters(): LiveData<List<Chapter>>

    @Query("SELECT * FROM ayat WHERE chapter = :chapterNo ORDER BY verse ASC")
    fun searchByChapterNo(chapterNo: Int): LiveData<List<Ayat>>

    @Query("SELECT * FROM chapter WHERE name = :name LIMIT 1")
    fun searchChapterByName(name: String): LiveData<Chapter>

    @Query("SELECT * FROM ayat ORDER BY RANDOM() LIMIT 1")
    fun getRandomAyah(): LiveData<Ayat>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAyat(ayat: Ayat)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addChapter(chapter: Chapter)

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun updateAyat(ayat: Ayat)

//    @Delete
//    fun deleteAyat(ayat: Ayat)

}