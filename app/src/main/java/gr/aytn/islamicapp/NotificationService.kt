package gr.aytn.islamicapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.IBinder
import android.os.TransactionTooLargeException
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.repository.PrayerRepository
import gr.aytn.islamicapp.ui.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : Service() {

    val formatterDate = SimpleDateFormat("dd-MM-yyyy")
    val formatter = SimpleDateFormat("HH:mm")
    val formatter2 = SimpleDateFormat("HH:mm:ss")

    var fajrTime = formatter.parse("00:00") as Date
    var sunriseTime = formatter.parse("00:00") as Date
    var dhuhrTime = formatter.parse("00:00") as Date
    var asrTime = formatter.parse("00:00") as Date
    var maghribTime = formatter.parse("00:00") as Date
    var ishaTime = formatter.parse("00:00") as Date
    var midnightTime = formatter.parse("00:00") as Date
    var midnightTime2 = formatter.parse("00:00") as Date

    var millis: Long = 1000

    var remaining_time: String = ""

    @Inject
    lateinit var repo: PrayerRepository

    val handler = Handler()
    private lateinit var runnable: Runnable
    private lateinit var notificationManager :NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("notf","in the onstart")


        var currentDate = "00-00-0000"
        var currentLocation = prefs.selected_location

        val delay: Long = 1000*5 // 1000 milliseconds == 1 second

        val contentView = RemoteViews(packageName, R.layout.notification)

        contentView.setTextViewText(R.id.notf_location, prefs.selected_location)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this@NotificationService,"sticky_notification")
            .setContent(contentView)
            .setSmallIcon(R.mipmap.isha_icon)
            .setOngoing(true)
            .setAutoCancel(false)

        val myintent = Intent(this@NotificationService, MainActivity::class.java)
        myintent.action = Intent.ACTION_MAIN
        myintent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent =
            PendingIntent.getActivity(this@NotificationService, 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT)


        builder.setContentIntent(pendingIntent)

        runnable = object : Runnable {
            override fun run() {
                Log.i("notfservie","in the run")
                val myCalendar: Calendar = Calendar.getInstance()
                val todaysDate = formatterDate.format(myCalendar.time)
                if(currentDate != todaysDate || currentLocation != prefs.selected_location){
                    Log.i("notfservie","date loc changed")

                    repo.getPrayerTimesByDate(todaysDate).observeForever {
                        if(it != null){
                            Log.i("notfservie","observing")
                            contentView.setTextViewText(R.id.notf_fajr, it.fajr)
                            contentView.setTextViewText(R.id.notf_sunrise, it.sunrise)
                            contentView.setTextViewText(R.id.notf_dhuhr, it.dhuhr)
                            contentView.setTextViewText(R.id.notf_asr, it.asr)
                            contentView.setTextViewText(R.id.notf_maghrib, it.maghrib)
                            contentView.setTextViewText(R.id.notf_isha, it.isha)

                            fajrTime = formatter.parse(it.fajr!!) as Date
                            sunriseTime = formatter.parse(it.sunrise!!) as Date
                            dhuhrTime = formatter.parse(it.dhuhr!!) as Date
                            asrTime = formatter.parse(it.asr!!) as Date
                            maghribTime = formatter.parse(it.maghrib!!) as Date
                            ishaTime = formatter.parse(it.isha!!) as Date
                            midnightTime = formatter.parse("24:00") as Date
                            midnightTime2 = formatter.parse("00:00") as Date
                            contentView.setTextViewText(R.id.notf_remaining_time, remaining_time)

                            currentDate = todaysDate
                            currentLocation = prefs.selected_location
                        }
                    }


//                notificationManager.notify(
//                    10,
//                    builder.build()
//                )

                }
                setPrayerTimes()
                contentView.setTextViewText(R.id.notf_remaining_time, remaining_time)
                startForeground(10, builder.build())
                handler.postDelayed(this, delay)
            }
        }

        handler.postDelayed(runnable, 0)



//        return super.onStartCommand(intent, flags, startId);
//        return START_REDELIVER_INTENT
        return START_STICKY
    }

    override fun onDestroy() {
        Log.i("servc","on destroy")
//        if(!prefs.sticky_notf){
//            handler.removeCallbacks(runnable)
//            stopSelf()
//        }
        prefs.sticky_notf = false
        handler.removeCallbacks(runnable)
        stopSelf()
        notificationManager.cancel(10)
        stopForeground(true)
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        println("onTaskRemoved called")
//        if (!prefs.sticky_notf){
//            println("onTaskRemoved service starting again")
//            val intent = Intent(applicationContext, NotificationService::class.java)
//            startService(intent)
//        }
        super.onTaskRemoved(rootIntent)

    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    fun setPrayerTimes(){
        val myCalendar: Calendar = Calendar.getInstance()
        val currentTime2: String = formatter2.format(myCalendar.getTime())
        val currentTimeDate: Date = formatter2.parse(currentTime2) as Date

        if(currentTimeDate.compareTo(fajrTime)>=0 && currentTimeDate.compareTo(sunriseTime)<0){
            millis = sunriseTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(sunriseTime)>=0 && currentTimeDate.compareTo(dhuhrTime)<0){
            millis = dhuhrTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(dhuhrTime)>=0 && currentTimeDate.compareTo(asrTime)<0){
            millis = asrTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(asrTime)>=0 && currentTimeDate.compareTo(maghribTime)<0){
            millis = maghribTime.time - currentTimeDate.time
            countdown(millis)

        }
        else if(currentTimeDate.compareTo(maghribTime)>=0 && currentTimeDate.compareTo(ishaTime)<0){
            millis = ishaTime.time - currentTimeDate.time
            countdown(millis)
        }

        else if((currentTimeDate.compareTo(ishaTime)>=0 && currentTimeDate.compareTo(midnightTime)<0)
            || (currentTimeDate.compareTo(midnightTime2)>=0 && currentTimeDate.compareTo(fajrTime)<0)){
            if(currentTimeDate.compareTo(ishaTime)>=0 && currentTimeDate.compareTo(midnightTime)<0){
                millis = (midnightTime.time - currentTimeDate.time) + (fajrTime.time - midnightTime2.time)
            }
            else if(currentTimeDate.compareTo(midnightTime2)>=0 && currentTimeDate.compareTo(fajrTime)<0){
                millis = fajrTime.time - currentTimeDate.time
            }
            countdown(millis)
        }
    }
    fun countdown(millis: Long){
        val timer = object: CountDownTimer(millis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000).toInt();
                val min = (millisUntilFinished / 60000).toInt() % 60
//                val sec = (millisUntilFinished / 1000).toInt() % 60
                remaining_time = String.format("- %02d:%02d", hour, min);
            }
            override fun onFinish() {
                setPrayerTimes()
            }
        }
        timer.start()
    }
}