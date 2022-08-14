package gr.aytn.islamicapp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var millis: Long = 0
    var tvRemainingTime: TextView? = null

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

        val tvCurrentPrayer: TextView = binding.currentPrayer
        val tvCurrentPrayerTime: TextView = binding.currentPrayerTime
        tvRemainingTime = binding.remainingTime

        val formatter = SimpleDateFormat("HH:mm")

        val currentTime: String = formatter.format(myCalendar.getTime())
        val currentTimeDate: Date = formatter.parse(currentTime) as Date

        val fajrTime: Date = formatter.parse(prefs.fajr_time) as Date
        val sunriseTime: Date = formatter.parse(prefs.sunrise_time) as Date
        val dhuhrTime: Date = formatter.parse(prefs.dhuhr_time) as Date
        val asrTime: Date = formatter.parse(prefs.asr_time) as Date
        val maghribTime: Date = formatter.parse(prefs.maghrib_time) as Date
        val ishaTime: Date = formatter.parse(prefs.isha_time) as Date

        if(currentTime.compareTo(prefs.fajr_time)>0 && currentTime.compareTo(prefs.sunrise_time)<0){
            tvCurrentPrayer.text = "Sübh Namazı"
            tvCurrentPrayerTime.text = prefs.fajr_time
            millis = sunriseTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTime.compareTo(prefs.sunrise_time)>0 && currentTime.compareTo(prefs.dhuhr_time)<0){
            tvCurrentPrayer.text = "Duha Namazı"
            tvCurrentPrayerTime.text = prefs.sunrise_time
            millis = dhuhrTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(dhuhrTime)>0 && currentTimeDate.compareTo(asrTime)<0){
            tvCurrentPrayer.text = "Zöhr Namazı"
            tvCurrentPrayerTime.text = prefs.dhuhr_time
            millis = asrTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTime.compareTo(prefs.asr_time)>0 && currentTime.compareTo(prefs.maghrib_time)<0){
            tvCurrentPrayer.text = "Əsr Namazı"
            tvCurrentPrayerTime.text = prefs.asr_time
            millis = maghribTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTime.compareTo(prefs.maghrib_time)>0 && currentTime.compareTo(prefs.isha_time)<0){
            tvCurrentPrayer.text = "Məğrib Namazı"
            tvCurrentPrayerTime.text = prefs.maghrib_time
            millis = ishaTime.time - currentTimeDate.time
            countdown(millis)
        }

        else if(currentTime.compareTo(prefs.isha_time)>0 && currentTime.compareTo(prefs.fajr_time)<0){
            tvCurrentPrayer.text = "İşa Namazı"
            tvCurrentPrayerTime.text = prefs.isha_time
            millis = fajrTime.time - currentTimeDate.time
            countdown(millis)
        }

        return root
    }
    fun countdown(millis: Long){
        val timer = object: CountDownTimer(millis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000).toInt();
                val min = (millisUntilFinished / 60000).toInt()
                tvRemainingTime?.text = String.format("- %02d:%02d:", hour, min);
            }
            override fun onFinish() {}
        }
        timer.start()
    }

}