package gr.aytn.islamicapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var millis: Long = 0
    var tvRemainingTime: TextView? = null
    var tvRemainingTimeSec: TextView? = null
    var layout: LinearLayout? = null
    var tvCheckAll: TextView? = null
    var currentPrayer: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val myCalendar: Calendar = Calendar.getInstance()
        val tz = TimeZone.getDefault()
        myCalendar.timeZone = tz

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val MONTHS: ArrayList<String> = arrayListOf("Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr")


        val tvCurrentPrayer: TextView = binding.currentPrayer
        val tvCurrentPrayerTime: TextView = binding.currentPrayerTime
        val tvDate: TextView = binding.homeDateTv
        tvCheckAll = binding.tvCheckAll
        tvRemainingTime = binding.remainingTime
        tvRemainingTimeSec = binding.remainingTimeSec
        layout = binding.homeLinearLayout

        val formatter = SimpleDateFormat("HH:mm")
        val formatter2 = SimpleDateFormat("HH:mm:ss")

        val currentTime: String = formatter2.format(myCalendar.getTime())
        val currentTimeDate: Date = formatter2.parse(currentTime) as Date

        val fajrTime: Date = formatter.parse(prefs.fajr_time) as Date
        val sunriseTime: Date = formatter.parse(prefs.sunrise_time) as Date
        val dhuhrTime: Date = formatter.parse(prefs.dhuhr_time) as Date
        val asrTime: Date = formatter.parse(prefs.asr_time) as Date
        val maghribTime: Date = formatter.parse(prefs.maghrib_time) as Date
        val ishaTime: Date = formatter.parse(prefs.isha_time) as Date

        tvDate.text = "$day ${MONTHS[month]} $year"
        tvCheckAll?.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main,PrayerFragment()).commit()
            }


        if(currentTimeDate.compareTo(fajrTime)>0 && currentTimeDate.compareTo(sunriseTime)<0){
            tvCurrentPrayer.text = "Sübh Namazı"
            currentPrayer = "fajr"
            tvCurrentPrayerTime.text = prefs.fajr_time
            millis = sunriseTime.time - currentTimeDate.time
            setBg(R.drawable.fajr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(sunriseTime)>0 && currentTimeDate.compareTo(dhuhrTime)<0){
            tvCurrentPrayer.text = "Duha Namazı"
            currentPrayer = "duha"
            tvCurrentPrayerTime.text = prefs.sunrise_time
            millis = dhuhrTime.time - currentTimeDate.time
            setBg(R.drawable.dhuhr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(dhuhrTime)>0 && currentTimeDate.compareTo(asrTime)<0){
            tvCurrentPrayer.text = "Zöhr Namazı"
            currentPrayer = "dhuhr"
            tvCurrentPrayerTime.text = prefs.dhuhr_time
            millis = asrTime.time - currentTimeDate.time
            setBg(R.drawable.dhuhr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(asrTime)>0 && currentTimeDate.compareTo(maghribTime)<0){
            tvCurrentPrayer.text = "Əsr Namazı"
            currentPrayer = "asr"
            tvCurrentPrayerTime.text = prefs.asr_time
            millis = maghribTime.time - currentTimeDate.time
            setBg(R.drawable.asr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(maghribTime)>0 && currentTimeDate.compareTo(ishaTime)<0){
            tvCurrentPrayer.text = "Məğrib Namazı"
            currentPrayer = "maghrib"
            tvCurrentPrayerTime.text = prefs.maghrib_time
            millis = ishaTime.time - currentTimeDate.time
            setBg(R.drawable.maghrib_bg_drawable)
            countdown(millis)
        }

        else if(currentTimeDate.compareTo(ishaTime)>0 && currentTimeDate.compareTo(fajrTime)<0){
            tvCurrentPrayer.text = "İşa Namazı"
            currentPrayer = "isha"
            tvCurrentPrayerTime.text = prefs.isha_time
            millis = fajrTime.time - currentTimeDate.time
            setBg(R.drawable.isha_bg_drawable)
            countdown(millis)
        }

        return root
    }
    fun countdown(millis: Long){
        val timer = object: CountDownTimer(millis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000).toInt();
                val min = (millisUntilFinished / 60000).toInt() % 60
                val sec = (millisUntilFinished / 1000).toInt() % 60
                tvRemainingTime?.text = String.format("- %02d:%02d:", hour, min);
                tvRemainingTimeSec?.text = sec.toString();
            }
            override fun onFinish() {}
        }
        timer.start()
    }
    fun setBg(bg: Int){
        layout?.setBackgroundResource(bg)
    }

}