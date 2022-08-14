package gr.aytn.islamicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gr.aytn.islamicapp.databinding.FragmentPrayerBinding
import gr.aytn.islamicapp.model.PrayerResponse
import java.text.SimpleDateFormat
import java.util.*

class PrayerFragment : Fragment() {


    private lateinit var fajrTime: TextView
    private lateinit var sunriseTime: TextView
    private lateinit var dhuhrTime: TextView
    private lateinit var asrTime: TextView
    private lateinit var maghribTime: TextView
    private lateinit var ishaTime: TextView
    private var myResponseList = ArrayList<PrayerResponse>()

    private lateinit var prayerViewModel: PrayerViewModel
    val myCalendar: Calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK)
        val hour = myCalendar.get(Calendar.HOUR)
        val minute = myCalendar.get(Calendar.MINUTE)
        val MONTHS: ArrayList<String> = arrayListOf("Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr")


        prayerViewModel = ViewModelProvider(this).get(PrayerViewModel::class.java)

        val binding = FragmentPrayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fajrTime = binding.fajrTime
        sunriseTime = binding.sunriseTime
        dhuhrTime = binding.dhuhrTime
        asrTime = binding.asrTime
        maghribTime = binding.maghribTime
        ishaTime = binding.ishaTime

        val tvDate = binding.prayerFragmentDate

        val sdf = SimpleDateFormat("EEEE")
        val d = Date()
        val dayOfTheWeek: String = weekday(sdf.format(d))

        tvDate.text = "$dayOfTheWeek, $day ${MONTHS[month]} $year"

        fajrTime.text = prefs.fajr_time
        sunriseTime.text = prefs.sunrise_time
        dhuhrTime.text = prefs.dhuhr_time
        asrTime.text = prefs.asr_time
        maghribTime.text = prefs.maghrib_time
        ishaTime.text = prefs.isha_time

        return root
    }

    fun weekday(weekday: String):String{
        var weekdayInAzeri: String = ""
        when(weekday){
            "Monday" -> weekdayInAzeri =  "Bazar ertəsi"
            "Tuesday" -> weekdayInAzeri =  "Çərşənbə axşamı"
            "Wednesday" -> weekdayInAzeri =  "Çərşənbə"
            "Thursday" -> weekdayInAzeri =  "Cümə axşamı"
            "Friday" -> weekdayInAzeri =  "Cümə"
            "Saturday" -> weekdayInAzeri =  "Şənbə"
            "Sunday" -> weekdayInAzeri =  "Bazar"
        }
        return  weekdayInAzeri
    }



}