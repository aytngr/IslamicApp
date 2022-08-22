package gr.aytn.islamicapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.aytn.islamicapp.data.PrayerDao
import gr.aytn.islamicapp.data.QuranDao
import gr.aytn.islamicapp.model.*
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PrayerRepository @Inject constructor(private val prayerDao: PrayerDao) {

    fun getPrayerTimesByDate(date: String): LiveData<PrayerTime> = prayerDao.getPrayerTimesByDate(date)
    fun addPrayerTimes(prayerTime: PrayerTime) = prayerDao.addPrayerTimes(prayerTime)
    fun updatePrayerTimes(prayerTime: PrayerTime)= prayerDao.updatePrayerTimes(prayerTime)
    fun getCount(): LiveData<Int> = prayerDao.getCount()
    fun deleteAllPrayerTimes() = prayerDao.deleteAllPrayerTimes()

    //    fun getQuran() = APIService?.getQuran()
    //    fun addAyat(ayat: Ayat) = quranDao.addAyat(ayat)
    //    private var APIService: APIService? = APIUtils().getAPIServiceForQuran()

}
