package gr.aytn.islamicapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.prefs
import java.util.*


class MainActivity : AppCompatActivity(), SettingsFragment.settingsLocationOnClickListener {

    private lateinit var currentFragment : Fragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavView.background = null

        val prayerViewModel = ViewModelProvider(this).get(PrayerViewModel::class.java)

        val myCalendar: Calendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) - 1
        val month = myCalendar.get(Calendar.MONTH) + 1

        prayerViewModel.getPrayerTimes("Azerbaijan","Baku","13",month.toString(),year.toString()).observe(this, Observer {
            if(day != 0){
                prefs.fajr_time_yesterday = it.data[day-1].timings!!.Fajr!!.substringBefore(" ")
                prefs.sunrise_time_yesterday = it.data[day-1].timings!!.Sunrise!!.substringBefore(" ")
                prefs.dhuhr_time_yesterday = it.data[day-1].timings!!.Dhuhr!!.substringBefore(" ")
                prefs.asr_time_yesterday = it.data[day-1].timings!!.Asr!!.substringBefore(" ")
                prefs.maghrib_time_yesterday = it.data[day-1].timings!!.Maghrib!!.substringBefore(" ")
                prefs.isha_time_yesterday = it.data[day-1].timings!!.Isha!!.substringBefore(" ")
            }else{
                prefs.warning_message = "Öncəki ay vaxtları üçün təqvimə baxın"
                prefs.fajr_time_yesterday = "00:00"
                prefs.sunrise_time_yesterday = "00:00"
                prefs.dhuhr_time_yesterday = "00:00"
                prefs.asr_time_yesterday = "00:00"
                prefs.maghrib_time_yesterday = "00:00"
                prefs.isha_time_yesterday = "00:00"
            }

            prefs.fajr_time = it.data[day].timings!!.Fajr!!.substringBefore(" ")
            prefs.sunrise_time = it.data[day].timings!!.Sunrise!!.substringBefore(" ")
            prefs.dhuhr_time = it.data[day].timings!!.Dhuhr!!.substringBefore(" ")
            prefs.asr_time = it.data[day].timings!!.Asr!!.substringBefore(" ")
            prefs.maghrib_time = it.data[day].timings!!.Maghrib!!.substringBefore(" ")
            prefs.isha_time = it.data[day].timings!!.Isha!!.substringBefore(" ")

            try{
                prefs.fajr_time_tomorrow = it.data[day+1].timings!!.Fajr!!.substringBefore(" ")
                prefs.sunrise_time_tomorrow = it.data[day+1].timings!!.Sunrise!!.substringBefore(" ")
                prefs.dhuhr_time_tomorrow = it.data[day+1].timings!!.Dhuhr!!.substringBefore(" ")
                prefs.asr_time_tomorrow = it.data[day+1].timings!!.Asr!!.substringBefore(" ")
                prefs.maghrib_time_tomorrow = it.data[day+1].timings!!.Maghrib!!.substringBefore(" ")
                prefs.isha_time_tomorrow = it.data[day+1].timings!!.Isha!!.substringBefore(" ")
            }catch (e: IndexOutOfBoundsException){
                prefs.warning_message = "Sonrakı ay vaxtları üçün təqvimə baxın"
                prefs.fajr_time_tomorrow = "00:00"
                prefs.sunrise_time_tomorrow = "00:00"
                prefs.dhuhr_time_tomorrow = "00:00"
                prefs.asr_time_tomorrow = "00:00"
                prefs.maghrib_time_tomorrow = "00:00"
                prefs.isha_time_tomorrow = "00:00"
            }
        })



        bottomNavView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    currentFragment= HomeFragment()

                }
                R.id.prayer -> {
                    currentFragment = PrayerFragment()
                }
//                R.id.quran -> {
//                    currentFragment = SettingsFragment()
//                }
//                R.id.dua -> {
//                    currentFragment = ProfileFragment()
//                }
                R.id.settings -> {
                    currentFragment = SettingsFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,currentFragment, "mytag").commit()
            true
        }

    }

    override fun settingsLocationOnClick() {
        val navHost: FragmentContainerView = findViewById(R.id.nav_host_fragment_settings)
        navHost.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_settings,LocationsFragment()).commit()

    }

}