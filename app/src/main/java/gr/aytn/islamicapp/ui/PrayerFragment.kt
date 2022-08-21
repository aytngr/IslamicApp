package gr.aytn.islamicapp.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentPrayerBinding
import gr.aytn.islamicapp.model.PrayerResponse
import gr.aytn.islamicapp.prefs
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
    val myCalendarYesterday: Calendar = Calendar.getInstance()
    val myCalendar: Calendar = Calendar.getInstance()
    val myCalendarTomorrow: Calendar = Calendar.getInstance()

    lateinit var line1:View
    lateinit var line2:View
    lateinit var warningMessage: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val MONTHS: ArrayList<String> = arrayListOf("Yanvar","Fevral","Mart","Aprel","May","İyun","İyul","Avqust","Sentyabr","Oktyabr","Noyabr","Dekabr")

        val formatterDayMonth = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("az"))

        val todayDateString = formatterDayMonth.format(myCalendar.time)
            .split(" ").joinToString(separator = " ", transform = String::capitalize)
//        val formatterMonth = SimpleDateFormat("M")
        myCalendarYesterday.add(Calendar.DATE, -1)
        val yesterdayMonthDayString = formatterDayMonth.format(myCalendarYesterday.time)
            .split(" ").joinToString(separator = " ", transform = String::capitalize)
//        val yesterdayDayString = formatterDayMonth.format(myCalendarYesterday.time)
        myCalendarTomorrow.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrowMonthDayString = formatterDayMonth.format(myCalendarTomorrow.time)
            .split(" ").joinToString(separator = " ", transform = String::capitalize)
//        val tomorrowDayString = formatterDayMonth.format(myCalendarTomorrow.time)
//        val yesterdayMonthDate: Date = formatterMonth.parse(yesterdayMonthString) as Date

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
        line1 = binding.view1
        line2 = binding.view2
        val tvDay = binding.tvDay
        warningMessage = binding.warning

//        val sdf = SimpleDateFormat("EEEE")
//        val d = Date()
//        val dayOfTheWeek: String = weekday(sdf.format(d))

        setPrayerTimes("today")
        tvDate.text = todayDateString

        val dayList = arrayListOf<String>("Dünən","Bugün","Sabah")
        var pos = 1
        leftBtn.setOnClickListener {
            if(pos != 0){
                pos -= 1
                tvDay.text = dayList[pos]
                if (pos == 1){
                    setPrayerTimes("today")
                    leftBtn.visibility = View.VISIBLE
                    tvDate.text = todayDateString
                    setViews("green")
                    warningMessage.visibility = View.GONE
                }else{
                    setPrayerTimes("yesterday")
                    leftBtn.visibility = View.GONE
                    if (prefs.warning_message == "no_yesterday"){
                        Log.i("wa", "no yesterday")
                        setWarning()
                    }else{
                        warningMessage.visibility = View.GONE
                    }
                    tvDate.text = yesterdayMonthDayString
                    setViews("red")
                }


            }
        }
        rightBtn.setOnClickListener {
            if(pos != 2){
                pos += 1
                tvDay.text = dayList[pos]
                if (pos == 1){
                    setPrayerTimes("today")
                    rightBtn.visibility = View.VISIBLE
                    tvDate.text = todayDateString
                    setViews("green")
                    warningMessage.visibility = View.GONE
                }else{
                    setPrayerTimes("tomorrow")
                    rightBtn.visibility = View.GONE
                    if (prefs.warning_message == "no_tomorrow"){
                        setWarning()
                    }else{
                        warningMessage.visibility = View.GONE
                    }
                    tvDate.text = tomorrowMonthDayString
                    setViews("red")
                }

            }
        }

        return root
    }
    fun setViews(color: String){
        when(color){
            "red" ->{
                line1.setBackgroundResource(R.drawable.line_bg_red)
                line2.setBackgroundResource(R.drawable.line_bg_red)
            }
            "green" ->{
                line1.setBackgroundResource(R.drawable.line_bg_green)
                line2.setBackgroundResource(R.drawable.line_bg_green)
            }
        }
    }
    fun setPrayerTimes(date: String){
        when(date){
            "yesterday" -> {
                fajrTime.text = prefs.fajr_time_yesterday
                sunriseTime.text = prefs.sunrise_time_yesterday
                dhuhrTime.text = prefs.dhuhr_time_yesterday
                asrTime.text = prefs.asr_time_yesterday
                maghribTime.text = prefs.maghrib_time_yesterday
                ishaTime.text = prefs.isha_time_yesterday
            }
            "today" -> {
                fajrTime.text = prefs.fajr_time
                sunriseTime.text = prefs.sunrise_time
                dhuhrTime.text = prefs.dhuhr_time
                asrTime.text = prefs.asr_time
                maghribTime.text = prefs.maghrib_time
                ishaTime.text = prefs.isha_time
            }
            "tomorrow" -> {
                fajrTime.text = prefs.fajr_time_tomorrow
                sunriseTime.text = prefs.sunrise_time_tomorrow
                dhuhrTime.text = prefs.dhuhr_time_tomorrow
                asrTime.text = prefs.asr_time_tomorrow
                maghribTime.text = prefs.maghrib_time_tomorrow
                ishaTime.text = prefs.isha_time_tomorrow
            }

        }

    }
    fun setWarning(){
        if (prefs.warning_message == "no_yesterday"){
            warningMessage.text = "Öncəki ay vaxtları üçün təqvimə baxın"
            warningMessage.visibility = View.VISIBLE
        }else if (prefs.warning_message == "no_tomorrow"){
            warningMessage.text = "Sonrakı ay vaxtları üçün təqvimə baxın"
            warningMessage.visibility = View.VISIBLE
        }else{
            warningMessage.visibility = View.GONE
        }
    }

//    fun weekday(weekday: String):String{
//        var weekdayInAzeri: String = ""
//        when(weekday){
//            "Monday" -> weekdayInAzeri =  "Bazar ertəsi"
//            "Tuesday" -> weekdayInAzeri =  "Çərşənbə axşamı"
//            "Wednesday" -> weekdayInAzeri =  "Çərşənbə"
//            "Thursday" -> weekdayInAzeri =  "Cümə axşamı"
//            "Friday" -> weekdayInAzeri =  "Cümə"
//            "Saturday" -> weekdayInAzeri =  "Şənbə"
//            "Sunday" -> weekdayInAzeri =  "Bazar"
//        }
//        return  weekdayInAzeri
//    }



}