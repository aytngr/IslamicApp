package gr.aytn.islamicapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gr.aytn.islamicapp.model.PrayerResponse
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrayerViewModel : ViewModel() {
    private lateinit var mAPIService: APIService
    private var myResponseList = MutableLiveData<PrayerResponse>()

    fun sendPost(): MutableLiveData<PrayerResponse> {
        mAPIService = APIUtils().getAPIService()!!

        val response = mAPIService.getPrayerTimes("Azerbaijan","Baku","13","08","2022",)
        response.enqueue(object: Callback<PrayerResponse> {
            override fun onResponse(call: Call<PrayerResponse>, response: Response<PrayerResponse>) {
                if (response.isSuccessful()){
                    myResponseList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<PrayerResponse>, t: Throwable) {
//                    tvResult?.text = "Unable to submit post to API."
            }

        })
        return myResponseList
    }
}