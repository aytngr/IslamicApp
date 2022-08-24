package gr.aytn.islamicapp.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.adapters.LocationAdapter
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
    var currentMonth = -1
    val formatterDate = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        bottomNavView = findViewById(R.id.bottomNavigationView)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        navController = navHostFragment?.findNavController()!!

        val myCalendar: Calendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) - 1
        val month = myCalendar.get(Calendar.MONTH) + 1

        navController.navigate(R.id.homeFragment)

        quranViewModel.getAllQuran()

        prayerViewModel.getCount().observe(this, Observer {
            Log.i("main", "$it")
        })
        Log.i("main", "")

    var currentMonth: Int = -1


        var todaysDate = formatterDate.format(myCalendar.time)

        if(currentMonth != month){
            Log.i("main","monht is diff")
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

        if(currentMonth != month){
            
        }

        Log.i("main","$currentMonth")


        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
//                    currentFragment= HomeFragment()
                    navController?.navigate(R.id.homeFragment)

                }
                R.id.prayer -> {
//                    currentFragment = PrayerFragment()
                    navController?.navigate(R.id.prayerFragment)
                }
                R.id.quran -> {
                    navController?.navigate(R.id.quranFragment)
                }
//                R.id.dua -> {
//                    currentFragment = ProfileFragment()
//                }
                R.id.settings -> {
//                    currentFragment = SettingsFragment()
                    navController?.navigate(R.id.settingsFragment)
                }
            }
//            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,currentFragment, "mytag").commit()
//            navController.navigate()
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
            finish()
        } else {
            super.onBackPressed()
        }

//        if (bottomNavView.selectedItemId == R.id.prayer || bottomNavView.selectedItemId == R.id.settings) {
//            bottomNavView.selectedItemId = R.id.home
//        } else {
//            super.onBackPressed()
//        }
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
        navController.navigate(R.id.settingsFragment)
        prefs.selected_location = location
    }


}