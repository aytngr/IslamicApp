package gr.aytn.islamicapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gr.aytn.islamicapp.model.PrayerResponse
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PrayerViewModel : ViewModel() {
    private lateinit var mAPIService: APIService
    private var myResponseList = MutableLiveData<PrayerResponse>()

    val myCalendar: Calendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val day = myCalendar.get(Calendar.DAY_OF_MONTH)
    val month = myCalendar.get(Calendar.MONTH)

    fun getPrayerTimes(country:String,city:String,method:String,month:String,year:String,context: Context): MutableLiveData<PrayerResponse> {
        mAPIService = APIUtils().getAPIService()!!

        val response = mAPIService.getPrayerTimes(city, country, method, month, year)
        response.enqueue(object: Callback<PrayerResponse> {
            override fun onResponse(call: Call<PrayerResponse>, response: Response<PrayerResponse>) {
                if (response.isSuccessful()){
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