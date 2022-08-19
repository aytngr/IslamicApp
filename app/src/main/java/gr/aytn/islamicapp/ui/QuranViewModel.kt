package gr.aytn.islamicapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.QuranResponse
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.network.APIUtils
import gr.aytn.islamicapp.repository.QuranRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class QuranViewModel @Inject constructor(private val repo: QuranRepository) : ViewModel() {
    private lateinit var mAPIService: APIService
    private lateinit var myResponse : QuranResponse

    fun getQuran(){
        mAPIService = APIUtils().getAPIServiceForQuran()!!
        Log.i("view", "getquran")
        val response = repo.getQuran()
        response?.enqueue(object: Callback<QuranResponse> {
            override fun onResponse(call: Call<QuranResponse>, response: Response<QuranResponse>) {
                if (response.isSuccessful()){
                    Log.i("view", "success")
                    myResponse = response.body()!!
                    for (i in 0..myResponse.quran.size-1){
                        viewModelScope.launch(Dispatchers.IO) {
                            repo.addAyat(Ayat(myResponse.quran[i].chapter,myResponse.quran[i].verse,myResponse.quran[i].text))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<QuranResponse>, t: Throwable) {
                Log.i("view", "onfailure")
            }

        })
    }
//    fun insertQuran(){
//        getQuran()
//        for (i in 0..myResponse.quran.size){
//            repo.addAyat(Ayat(myResponse.quran[i].chapter,myResponse.quran[i].verse,myResponse.quran[i].text))
//        }
//    }
    fun searchByChapterNo(chapterNo: Int): LiveData<List<Ayat>> = repo.searchByChapterNo(chapterNo)

}