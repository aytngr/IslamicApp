package gr.aytn.islamicapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import gr.aytn.islamicapp.config.Prefs
import com.gu.toolargetool.TooLargeTool;

val prefs: Prefs by lazy {
    BaseApplication.prefs!!
}

@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        TooLargeTool.startLogging(this);

        instance = this
        prefs = Prefs(applicationContext)
    }
}