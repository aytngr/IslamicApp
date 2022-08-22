package gr.aytn.islamicapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.model.PrayerTime

@Dao
interface PrayerDao {

    @Query("SELECT COUNT(*) FROM prayertime")
    fun getCount(): LiveData<Int>

    @Query("DELETE FROM prayertime")
    fun deleteAllPrayerTimes()

    @Query("SELECT * FROM prayertime WHERE date = :date LIMIT 1")
    fun getPrayerTimesByDate(date: String): LiveData<PrayerTime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPrayerTimes(prayerTime: PrayerTime)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePrayerTimes(prayerTime: PrayerTime)

//    @Delete
//    fun deleteAyat(ayat: Ayat)

}