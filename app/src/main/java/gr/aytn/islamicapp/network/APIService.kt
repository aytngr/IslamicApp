package gr.aytn.islamicapp.network

import gr.aytn.islamicapp.model.PrayerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("calendarByCity?")
    fun getPrayerTimes(@Query("city") city: String?,@Query("country") country: String?,
                         @Query("method") method: String?, @Query("month") month: String?,
                         @Query("year") year: String?): Call<PrayerResponse>
}