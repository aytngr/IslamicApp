package gr.aytn.islamicapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

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
        val month = myCalendar.get(Calendar.MONTH)

        prayerViewModel.sendPost().observe(this, Observer {
            prefs.fajr_time = it.data[day].timings!!.Fajr!!.substringBefore(" ")
            prefs.sunrise_time = it.data[day].timings!!.Sunrise!!.substringBefore(" ")
            prefs.dhuhr_time = it.data[day].timings!!.Dhuhr!!.substringBefore(" ")
            prefs.asr_time = it.data[day].timings!!.Asr!!.substringBefore(" ")
            prefs.maghrib_time = it.data[day].timings!!.Maghrib!!.substringBefore(" ")
            prefs.isha_time = it.data[day].timings!!.Isha!!.substringBefore(" ")
        })



        bottomNavView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    currentFragment = HomeFragment()
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
//                R.id.settings -> {
//                    currentFragment = ProfileFragment()
//                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,currentFragment).commit()
            true
        }

    }
}