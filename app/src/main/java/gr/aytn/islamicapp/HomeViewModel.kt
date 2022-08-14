package gr.aytn.islamicapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gr.aytn.islamicapp.model.PrayerResponse

class HomeViewModel : ViewModel() {

    private var diff = MutableLiveData<String>()

    fun findDiff(millis: Long): MutableLiveData<String> {
        val hours = (millis / (1000 * 60 * 60)).toInt()
        val mins = (millis / (1000 * 60)).toInt() % 60
        diff.postValue("$hours:$mins")
        return diff
    }

}