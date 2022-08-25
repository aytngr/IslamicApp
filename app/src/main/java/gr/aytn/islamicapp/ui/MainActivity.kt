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
        val intent = Intent(this, NotificationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }

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
        navController.navigate(R.id.settingsFragment)

    }

    fun createNotification() {
        val notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val contentView = RemoteViews(packageName, R.layout.notification)
        contentView.setTextViewText(R.id.notf_fajr, prefs.fajr_time)
        contentView.setTextViewText(R.id.notf_sunrise, prefs.sunrise_time)
        contentView.setTextViewText(R.id.notf_dhuhr, prefs.dhuhr_time)
        contentView.setTextViewText(R.id.notf_asr, prefs.asr_time)
        contentView.setTextViewText(R.id.notf_maghrib, prefs.maghrib_time)
        contentView.setTextViewText(R.id.notf_isha, prefs.isha_time)
        contentView.setTextViewText(R.id.notf_location, prefs.selected_location)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,"sticky_notification")
            .setContent(contentView)
            .setSmallIcon(R.drawable.asr_icon)
            .setOngoing(true)
            .setAutoCancel(false)
        val intent = Intent(this, MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        /*Notification noti = builder.build();
    noti.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;*/
        notificationManager.notify(
            10,
            builder.build()
        )
    }

    fun updateNotificationText(inString: String?) {
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentView = RemoteViews(packageName, R.layout.notification)
        contentView.setTextViewText(R.id.notf_fajr, prefs.fajr_time)
        contentView.setTextViewText(R.id.notf_sunrise, prefs.sunrise_time)
        contentView.setTextViewText(R.id.notf_dhuhr, prefs.dhuhr_time)
        contentView.setTextViewText(R.id.notf_asr, prefs.asr_time)
        contentView.setTextViewText(R.id.notf_maghrib, prefs.maghrib_time)
        contentView.setTextViewText(R.id.notf_isha, prefs.isha_time)
        contentView.setTextViewText(R.id.notf_location, prefs.selected_location)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,"sticky_notification")
            .setContent(contentView)
            .setSmallIcon(R.drawable.asr_icon)
            .setOngoing(true)
            .setAutoCancel(false)
        val intent = Intent(this, MainActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        /*Notification noti = builder.build();
    noti.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;*/
        notificationManager.notify(
            10,
            builder.build()
        )
    }

    fun cancelNotification() {

    }

//    override fun onResume() {
//        super.onResume()
//        val myCalendar: Calendar = Calendar.getInstance()
//        var todaysDate = formatterDate.format(myCalendar.time)
//        prayerViewModel.getPrayerTimesByDate(todaysDate).observe(this, Observer {
//            if(it != null){
//                prefs.fajr_time = it.fajr!!
//                prefs.sunrise_time = it.sunrise!!
//                prefs.dhuhr_time = it.dhuhr!!
//                prefs.asr_time = it.asr!!
//                prefs.maghrib_time = it.maghrib!!
//                prefs.isha_time = it.isha!!
//            }
//
//        })
//        createNotification()
//    }


}