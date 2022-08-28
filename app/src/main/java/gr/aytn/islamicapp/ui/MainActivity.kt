package gr.aytn.islamicapp.ui


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.NotificationService
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.LocationAdapter
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.prefs
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HomeFragment.checkAllBtnOnClickListener,
    LocationAdapter.OnLocationItemClickListener {

    private lateinit var currentFragment: Fragment
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navController: NavController
    val quranViewModel: QuranViewModel by viewModels()
    val prayerViewModel: PrayerViewModel by viewModels()
    private lateinit var homeViewModel: HomeViewModel
    var currentMonth = -1
    val formatterDate = SimpleDateFormat("dd-MM-yyyy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (prefs.theme == "Light"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else if (prefs.theme == "Dark"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

//        createNotification()





        bottomNavView = findViewById(R.id.bottomNavigationView)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        navController = navHostFragment?.findNavController()!!

        val myCalendar: Calendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) - 1
        val month = myCalendar.get(Calendar.MONTH) + 1

        quranViewModel.getAllQuran()

        quranViewModel.getRandomAyah().observe(this, androidx.lifecycle.Observer{
            if(it != null){
                prefs.random_ayah = "${it.verse}. ${it.text}. (${Constants.getChapterList().get(it.chapter!!-1).name} surəsi)"
            }
        })

        var todaysDate = formatterDate.format(myCalendar.time)

        if(currentMonth != month){
            currentMonth = month
            prayerViewModel.getPrayerTimes(
                "Azerbaijan",
                "Baku",
                "13",
                month.toString(),
                year.toString(),
                this
            ).observe(this, Observer {
                todaysDate = formatterDate.format(myCalendar.time)
                prayerViewModel.getPrayerTimesByDate(todaysDate).observe(this, Observer {
                    if(it!=null){
                        prefs.fajr_time = it.fajr!!
                        prefs.sunrise_time = it.sunrise!!
                        prefs.dhuhr_time = it.dhuhr!!
                        prefs.asr_time = it.asr!!
                        prefs.maghrib_time = it.maghrib!!
                        prefs.isha_time = it.isha!!
                    }else{
                        prefs.fajr_time = "00:00"
                        prefs.sunrise_time = "00:00"
                        prefs.dhuhr_time = "00:00"
                        prefs.asr_time = "00:00"
                        prefs.maghrib_time = "00:00"
                        prefs.isha_time = "00:00"
                    }

                })
            })
        }
/////////////////////////////////////////////////
        val notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        if(prefs.sticky_notf){
//            val intent = Intent(this, NotificationService::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                startForegroundService(intent);
//            } else {
//                startService(intent);
//            }
//        }
//////////////////////////////////////////
        prayerViewModel.getPrayerTimesByDate(todaysDate).observe(this, Observer {
            if(it != null){
                prefs.fajr_time = it.fajr!!
                prefs.sunrise_time = it.sunrise!!
                prefs.dhuhr_time = it.dhuhr!!
                prefs.asr_time = it.asr!!
                prefs.maghrib_time = it.maghrib!!
                prefs.isha_time = it.isha!!
            }

        })

        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController?.navigate(R.id.homeFragment)

                }
                R.id.prayer -> {
                    navController?.navigate(R.id.prayerFragment)
                }
                R.id.quran -> {
                    navController?.navigate(R.id.quranFragment)
                }
//                R.id.dua -> {
//                    currentFragment = ProfileFragment()
//                }
                R.id.settings -> {
                    navController?.navigate(R.id.settingsFragment)
                }
            }
            true
        }
        navController?.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            // the IDs of fragments as defined in the `navigation_graph`
            if (nd.id == R.id.homeFragment || nd.id == R.id.prayerFragment
                || nd.id == R.id.settingsFragment || nd.id == R.id.quranFragment
            ) {
                bottomNavView.visibility = View.VISIBLE
            } else {
                bottomNavView.visibility = View.GONE
            }
        }

    }

    override fun checkAllBtnOnClick() {
        bottomNavView.selectedItemId = R.id.prayer
    }

    override fun onBackPressed() {

        if (navController.currentDestination?.id == R.id.prayerFragment ||
            navController.currentDestination?.id == R.id.settingsFragment ||
            navController.currentDestination?.id == R.id.quranFragment
        ) {
            bottomNavView.selectedItemId = R.id.home
        } else if (navController.currentDestination?.id == R.id.chapterFragment) {
            bottomNavView.selectedItemId = R.id.quran
        } else if (navController.currentDestination?.id == R.id.homeFragment) {
            prefs.chapter_no = 0
            finish()
        } else {
            super.onBackPressed()
        }

    }

    override fun onLocationClick(location: String) {
        val myCalendar: Calendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH) + 1

        val todaysDate = formatterDate.format(myCalendar.time)

        prayerViewModel.getPrayerTimes(
            "Azerbaijan",
            location,
            "13",
            month.toString(),
            year.toString(),
            this
        ).observe(this, Observer {
            prayerViewModel.getPrayerTimesByDate(todaysDate).observe(this, Observer {
                if(it!=null){
                    prefs.fajr_time = it.fajr!!
                    prefs.sunrise_time = it.sunrise!!
                    prefs.dhuhr_time = it.dhuhr!!
                    prefs.asr_time = it.asr!!
                    prefs.maghrib_time = it.maghrib!!
                    prefs.isha_time = it.isha!!
                    prefs.selected_location = location
                }else{
                    prefs.fajr_time = "00:00"
                    prefs.sunrise_time = "00:00"
                    prefs.dhuhr_time = "00:00"
                    prefs.asr_time = "00:00"
                    prefs.maghrib_time = "00:00"
                    prefs.isha_time = "00:00"
                }

            })
        })
        prefs.selected_location = location
        navController.navigate(R.id.settingsFragment)

    }





}