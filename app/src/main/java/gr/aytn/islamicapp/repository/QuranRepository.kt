package gr.aytn.islamicapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.aytn.islamicapp.data.QuranDao
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.model.PrayerResponse
import gr.aytn.islamicapp.model.QuranResponse
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class QuranRepository @Inject constructor(private val quranDao: QuranDao) {

    fun searchByChapterNo(chapterNo: Int): LiveData<List<Ayat>> = quranDao.searchByChapterNo(chapterNo)
    fun getAllQuran(): LiveData<List<Ayat>> = quranDao.getAllQuran()
    fun getAllChapters(): LiveData<List<Chapter>> = quranDao.getAllChapters()
    fun getRandomAyah(): LiveData<Ayat> = quranDao.getRandomAyah()
    fun searchChapterByName(name:String): LiveData<Chapter> = quranDao.searchChapterByName(name)

    //    fun getQuran() = APIService?.getQuran()
    //    fun addAyat(ayat: Ayat) = quranDao.addAyat(ayat)
    //    private var APIService: APIService? = APIUtils().getAPIServiceForQuran()

}
