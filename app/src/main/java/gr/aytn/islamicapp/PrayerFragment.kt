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
        val leftBtn = binding.left
        val rightBtn = binding.right
        var line1 = binding.view1
        var line2 = binding.view2
        val tvDay = binding.tvDay

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

        val dayList = arrayListOf<String>("Dünən","Bugün","Sabah")
        var pos = 1
        leftBtn.setOnClickListener {
            if(pos != 0){
                pos -= 1
                tvDay.text = dayList[pos]
                if (pos == 1){
                    line1.setBackgroundResource(R.drawable.line_bg_green)
                    line2.setBackgroundResource(R.drawable.line_bg_green)
                }else{
                    line1.setBackgroundResource(R.drawable.line_bg_red)
                    line2.setBackgroundResource(R.drawable.line_bg_red)
                }


            }
        }
        rightBtn.setOnClickListener {
            if(pos != 2){
                pos += 1
                tvDay.text = dayList[pos]
                if (pos == 1){
                    line1.setBackgroundResource(R.drawable.line_bg_green)
                    line2.setBackgroundResource(R.drawable.line_bg_green)
                }else{
                    line1.setBackgroundResource(R.drawable.line_bg_red)
                    line2.setBackgroundResource(R.drawable.line_bg_red)
                }

            }
        }

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