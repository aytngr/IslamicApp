package gr.aytn.islamicapp.ui

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RemoteViews
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import gr.aytn.islamicapp.prefs
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val prayerViewModel: PrayerViewModel by viewModels()
    val formatterDate = SimpleDateFormat("dd-MM-yyyy")

    var timer: CountDownTimer? = null

    private lateinit var homeViewModel: HomeViewModel
    var millis: Long = 0
    var tvRemainingTime: TextView? = null
    var tvRemainingTimeSec: TextView? = null
    var tvIndi: TextView? = null
    var tvVaxtidir: TextView? = null
    var layout: LinearLayout? = null
    var tvCheckAll: TextView? = null
    var currentPrayer: String = ""

    var myCalendar1: Calendar = Calendar.getInstance()

    val formatter = SimpleDateFormat("HH:mm")
    val formatter2 = SimpleDateFormat("HH:mm:ss")

    val fajrTime: Date = formatter.parse(prefs.fajr_time) as Date
    val sunriseTime: Date = formatter.parse(prefs.sunrise_time) as Date
    val duhaTime: Date = formatter.parse(prefs.duha_time) as Date
    val dhuhrTime: Date = formatter.parse(prefs.dhuhr_time) as Date
    val asrTime: Date = formatter.parse(prefs.asr_time) as Date
    val maghribTime: Date = formatter.parse(prefs.maghrib_time) as Date
    val ishaTime: Date = formatter.parse(prefs.isha_time) as Date
    val midnightTime: Date = formatter.parse("24:00") as Date
    val midnightTime2: Date = formatter.parse("00:00") as Date

    var tvCurrentPrayer: TextView?=null
    var tvCurrentPrayerTime: TextView? =null

    var cardTopTextViews: LinearLayout? =null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val tz = TimeZone.getDefault()
        myCalendar1.timeZone = tz

        cardTopTextViews = binding.cardTop

        tvIndi = binding.tvIndi
        tvVaxtidir = binding.tvVaxtidir

        val year = myCalendar1.get(Calendar.YEAR)
        val month = myCalendar1.get(Calendar.MONTH)
        val day = myCalendar1.get(Calendar.DAY_OF_MONTH)
        val MONTHS: ArrayList<String> = arrayListOf("Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr")

//        createNotification()

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

        tvRandomAyat.text = prefs.random_ayah

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
        else if(currentTimeDate.compareTo(sunriseTime)>=0 && currentTimeDate.time - sunriseTime.time<40*60000){
            tvCurrentPrayer?.text = "Gün çıxır"
            currentPrayer = "sunrise"
            tvCurrentPrayerTime?.text = prefs.sunrise_time
            millis = dhuhrTime.time - currentTimeDate.time
            setBg(R.drawable.dhuhr_bg_drawable)
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
        timer = object: CountDownTimer(millis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000).toInt();
                val min = (millisUntilFinished / 60000).toInt() % 60
                val sec = (millisUntilFinished / 1000).toInt() % 60
                if(currentPrayer == "sunrise"){
                    tvRemainingTime?.visibility = View.GONE
                    tvRemainingTimeSec?.visibility = View.GONE
                    tvIndi?.visibility = View.GONE
                    tvVaxtidir?.visibility = View.GONE
                }else{
                    tvRemainingTime?.visibility = View.VISIBLE
                    tvRemainingTimeSec?.visibility = View.VISIBLE
                    tvIndi?.visibility = View.VISIBLE
                    tvVaxtidir?.visibility = View.VISIBLE
                    tvRemainingTime?.text = String.format("- %02d:%02d:", hour, min);
                    tvRemainingTimeSec?.text = sec.toString();
                }
            }
            override fun onFinish() {
                setPrayerTimes()
            }
        }
        timer?.start()

    }
    fun setBg(bg: Int){
        layout?.setBackgroundResource(bg)
    }

    interface checkAllBtnOnClickListener{
        fun checkAllBtnOnClick()
    }

    fun updateTimes(){
        val todaysDate = formatterDate.format(myCalendar1.time)
        prayerViewModel.getPrayerTimesByDate(todaysDate).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                prefs.fajr_time = it.fajr!!
                prefs.sunrise_time = it.sunrise!!
                prefs.dhuhr_time = it.dhuhr!!
                prefs.asr_time = it.asr!!
                prefs.maghrib_time = it.maghrib!!
                prefs.isha_time = it.isha!!
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tvCheckAll = null
        layout = null
        cardTopTextViews = null
        tvRemainingTime = null
        tvRemainingTimeSec = null
        timer?.cancel()
        timer = null
    }
}