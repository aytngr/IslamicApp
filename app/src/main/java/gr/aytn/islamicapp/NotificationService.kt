package gr.aytn.islamicapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.islamicapp.ui.MainActivity
import gr.aytn.islamicapp.ui.PrayerViewModel

@AndroidEntryPoint
class NotificationService : Service() {

    val prayerViewModel: PrayerViewModel by viewModels()

    override fun onCreate() {
        startForeground(9999, Notification())
    }

    // execution of service will start
    // on calling this method
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        prayerViewModel.getPrayerTimesByDate(todaysDate).observe(this, Observer {
            if(it != null){
                contentView.setTextViewText(R.id.notf_fajr, it.fajr!!)
                contentView.setTextViewText(R.id.notf_sunrise, prefs.sunrise_time)
                contentView.setTextViewText(R.id.notf_dhuhr, prefs.dhuhr_time)
                contentView.setTextViewText(R.id.notf_asr, prefs.asr_time)
                contentView.setTextViewText(R.id.notf_maghrib, prefs.maghrib_time)
                contentView.setTextViewText(R.id.notf_isha, prefs.isha_time)
                contentView.setTextViewText(R.id.notf_location, prefs.selected_location)

            }

        })

        val notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val contentView = RemoteViews(packageName, R.layout.notification)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,"sticky_notification")
            .setContent(contentView)
            .setSmallIcon(R.drawable.asr_icon)
            .setOngoing(true)
            .setAutoCancel(false)
        val myintent = Intent(this, MainActivity::class.java)
        myintent.action = Intent.ACTION_MAIN
        myintent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        notificationManager.notify(
            10,
            builder.build()
        )
        return START_STICKY
    }

    // execution of the service will
    // stop on calling this method
    override fun onDestroy() {
        super.onDestroy()
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(10)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}