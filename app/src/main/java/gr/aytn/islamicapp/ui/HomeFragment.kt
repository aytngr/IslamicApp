package gr.aytn.islamicapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import gr.aytn.islamicapp.prefs
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val quranViewModel: QuranViewModel by viewModels()

    private lateinit var homeViewModel: HomeViewModel
    var millis: Long = 0
    var tvRemainingTime: TextView? = null
    var tvRemainingTimeSec: TextView? = null
    var layout: LinearLayout? = null
    var tvCheckAll: TextView? = null
    var currentPrayer: String = ""

    var myCalendar1: Calendar = Calendar.getInstance()

    val formatter = SimpleDateFormat("HH:mm")
    val formatter2 = SimpleDateFormat("HH:mm:ss")

    val fajrTime: Date = formatter.parse(prefs.fajr_time) as Date
    val sunriseTime: Date = formatter.parse(prefs.sunrise_time) as Date
    val dhuhrTime: Date = formatter.parse(prefs.dhuhr_time) as Date
    val asrTime: Date = formatter.parse(prefs.asr_time) as Date
    val maghribTime: Date = formatter.parse(prefs.maghrib_time) as Date
    val ishaTime: Date = formatter.parse(prefs.isha_time) as Date
    val midnightTime: Date = formatter.parse("24:00") as Date
    val midnightTime2: Date = formatter.parse("00:00") as Date

    var tvCurrentPrayer: TextView?=null
    var tvCurrentPrayerTime: TextView? =null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val tz = TimeZone.getDefault()
        myCalendar1.timeZone = tz

        val year = myCalendar1.get(Calendar.YEAR)
        val month = myCalendar1.get(Calendar.MONTH)
        val day = myCalendar1.get(Calendar.DAY_OF_MONTH)
        val MONTHS: ArrayList<String> = arrayListOf("Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr")

        val tvRandomAyat = binding.randomAyat

        tvCurrentPrayer = binding.currentPrayer
        tvCurrentPrayerTime = binding.currentPrayerTime
        val tvDate: TextView = binding.homeDateTv
        tvCheckAll = binding.tvCheckAll
        tvRemainingTime = binding.remainingTime
        tvRemainingTimeSec = binding.remainingTimeSec
        layout = binding.homeLinearLayout
         val selectedLocation = binding.homeSelectedLocation

        selectedLocation.text = prefs.selected_location

        tvDate.text = "$day ${MONTHS[month]} $year"

        quranViewModel.getRandomAyah().observe(viewLifecycleOwner, androidx.lifecycle.Observer{
            if(it != null){
                tvRandomAyat.text = "${it.verse}. ${it.text}. (${Constants.getChapterList().get(it.chapter!!).name} surəsi)"

            }
        })

        val listener: checkAllBtnOnClickListener = activity as checkAllBtnOnClickListener
        tvCheckAll?.setOnClickListener {
//            findNavController().navigate(R.id.prayerFragment)
            listener.checkAllBtnOnClick()
        }
        setPrayerTimes()

        return root
    }
    fun setPrayerTimes(){
        val myCalendar: Calendar = Calendar.getInstance()
        val currentTime: String = formatter2.format(myCalendar.getTime())
        val currentTimeDate: Date = formatter2.parse(currentTime) as Date

        Log.i("home", currentTime)

        if(currentTimeDate.compareTo(fajrTime)>=0 && currentTimeDate.compareTo(sunriseTime)<0){
            tvCurrentPrayer?.text = "Sübh Namazı"
            currentPrayer = "fajr"
            tvCurrentPrayerTime?.text = prefs.fajr_time
            millis = sunriseTime.time - currentTimeDate.time
            setBg(R.drawable.fajr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(sunriseTime)>=0 && currentTimeDate.compareTo(dhuhrTime)<0){
            tvCurrentPrayer?.text = "Duha Namazı"
            currentPrayer = "duha"
            tvCurrentPrayerTime?.text = prefs.sunrise_time
            millis = dhuhrTime.time - currentTimeDate.time
            setBg(R.drawable.dhuhr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(dhuhrTime)>=0 && currentTimeDate.compareTo(asrTime)<0){
            tvCurrentPrayer?.text = "Zöhr Namazı"
            currentPrayer = "dhuhr"
            tvCurrentPrayerTime?.text = prefs.dhuhr_time
            millis = asrTime.time - currentTimeDate.time
            setBg(R.drawable.dhuhr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(asrTime)>=0 && currentTimeDate.compareTo(maghribTime)<0){
            tvCurrentPrayer?.text = "Əsr Namazı"
            currentPrayer = "asr"
            tvCurrentPrayerTime?.text = prefs.asr_time
            millis = maghribTime.time - currentTimeDate.time
            setBg(R.drawable.asr_bg_drawable)
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(maghribTime)>=0 && currentTimeDate.compareTo(ishaTime)<0){
            tvCurrentPrayer?.text = "Məğrib Namazı"
            currentPrayer = "maghrib"
            tvCurrentPrayerTime?.text = prefs.maghrib_time
            millis = ishaTime.time - currentTimeDate.time
            setBg(R.drawable.maghrib_bg_drawable)
            countdown(millis)
        }

        else if((currentTimeDate.compareTo(ishaTime)>=0 && currentTimeDate.compareTo(midnightTime)<0)
            || (currentTimeDate.compareTo(midnightTime2)>=0 && currentTimeDate.compareTo(fajrTime)<0)){
            tvCurrentPrayer?.text = "İşa Namazı"
            currentPrayer = "isha"
            tvCurrentPrayerTime?.text = prefs.isha_time
            if(currentTimeDate.compareTo(ishaTime)>=0 && currentTimeDate.compareTo(midnightTime)<0){
                millis = (midnightTime.time - currentTimeDate.time) + (fajrTime.time - midnightTime2.time)
            }
            else if(currentTimeDate.compareTo(midnightTime2)>=0 && currentTimeDate.compareTo(fajrTime)<0){
                millis = fajrTime.time - currentTimeDate.time
            }

            setBg(R.drawable.isha_bg_drawable)
            countdown(millis)
        }
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
            override fun onFinish() {
                setPrayerTimes()
            }
        }
        timer.start()
    }
    fun setBg(bg: Int){
        layout?.setBackgroundResource(bg)
    }

    interface checkAllBtnOnClickListener{
        fun checkAllBtnOnClick()
    }

}