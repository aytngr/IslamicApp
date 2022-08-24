package gr.aytn.islamicapp.ui

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.model.PrayerResponse
import gr.aytn.islamicapp.model.PrayerTime
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import gr.aytn.islamicapp.repository.PrayerRepository
import gr.aytn.islamicapp.repository.QuranRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(private val resources: Resources, private val repo: PrayerRepository): ViewModel() {
    private lateinit var mAPIService: APIService
    private var myResponseList = MutableLiveData<PrayerResponse>()

    val myCalendar: Calendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val day = myCalendar.get(Calendar.DAY_OF_MONTH)
    val month = myCalendar.get(Calendar.MONTH)

    fun getPrayerTimesByDate(date: String): LiveData<PrayerTime> = repo.getPrayerTimesByDate(date)
    fun getCount(): LiveData<Int> = repo.getCount()
    fun getPrayerTimes(country:String,city:String,method:String,month:String,year:String,context: Context): MutableLiveData<PrayerResponse> {
        mAPIService = APIUtils().getAPIService()!!

        val response = mAPIService.getPrayerTimes(city, country, method, month, year)
        response.enqueue(object: Callback<PrayerResponse> {
            override fun onResponse(call: Call<PrayerResponse>, response: Response<PrayerResponse>) {
                if (response.isSuccessful){
                    val prayerResponse = response.body()!!
                    viewModelScope.launch(Dispatchers.IO) {
                        repo.deleteAllPrayerTimes()
                    }
                    for(i in 0 until prayerResponse.data.size){
                        val date = prayerResponse.data[i].date!!.gregorian!!.date
                        val fajr = prayerResponse.data[i].timings!!.Imsak!!.substringBefore(" ")
                        val sunrise = prayerResponse.data[i].timings!!.Sunrise!!.substringBefore(" ")
                        val dhuhr = prayerResponse.data[i].timings!!.Dhuhr!!.substringBefore(" ")
                        val asr = prayerResponse.data[i].timings!!.Asr!!.substringBefore(" ")
                        val maghrib = prayerResponse.data[i].timings!!.Maghrib!!.substringBefore(" ")
                        val isha = prayerResponse.data[i].timings!!.Isha!!.substringBefore(" ")

                        val prayerTimeEntity = PrayerTime(
                            date,fajr, sunrise, dhuhr, asr, maghrib, isha
                        )
                        viewModelScope.launch(Dispatchers.IO){
                            repo.addPrayerTimes(prayerTimeEntity)
                        }
                    }
                    myResponseList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PrayerResponse>, t: Throwable) {
                    Toast.makeText(context,"Internet bağlantısı yoxdur",Toast.LENGTH_SHORT).show()
            }

        })
        return myResponseList
    }

}