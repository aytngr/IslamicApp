package gr.aytn.islamicapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.islamicapp.prefs

class HomeViewModel : ViewModel() {

    private var diff = MutableLiveData<String>()

    fun remainingTime(): MutableLiveData<String> {
        diff.postValue(prefs.remaining_time)
        return diff
    }

}