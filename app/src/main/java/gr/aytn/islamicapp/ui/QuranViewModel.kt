package gr.aytn.islamicapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.islamicapp.config.Constants
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.model.QuranResponse
import gr.aytn.islamicapp.network.APIService
import gr.aytn.islamicapp.repository.QuranRepository
import javax.inject.Inject


@HiltViewModel
class QuranViewModel @Inject constructor(private val repo: QuranRepository) : ViewModel() {
    private lateinit var mAPIService: APIService
    private lateinit var myResponse : QuranResponse
    private lateinit var verseNamesList : MutableLiveData<ArrayList<Chapter>>
    val constants = Constants()

    fun searchByChapterNo(chapterNo: Int): LiveData<List<Ayat>> = repo.searchByChapterNo(chapterNo)
    fun getAllQuran(): LiveData<List<Ayat>> = repo.getAllQuran()

//    fun getVerseList(): MutableLiveData<ArrayList<Chapter>>{
//        verseNamesList.postValue(constants.getChapterList())
//        return verseNamesList
//    }


//    fun getQuran(){
//        mAPIService = APIUtils().getAPIServiceForQuran()!!
//        Log.i("view", "getquran")
//        val response = repo.getQuran()
//        response?.enqueue(object: Callback<QuranResponse> {
//            override fun onResponse(call: Call<QuranResponse>, response: Response<QuranResponse>) {
//                if (response.isSuccessful()){
//                    Log.i("view", "success")
//                    myResponse = response.body()!!
//                    for (i in 0..myResponse.quran.size-1){
//                        viewModelScope.launch(Dispatchers.IO) {
//                            repo.addAyat(Ayat(myResponse.quran[i].chapter,myResponse.quran[i].verse,myResponse.quran[i].text))
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<QuranResponse>, t: Throwable) {
//                Log.i("view", "onfailure")
//            }
//
//        })
//    }
//    fun insertQuran(){
//        getQuran()
//        for (i in 0..myResponse.quran.size){
//            repo.addAyat(Ayat(myResponse.quran[i].chapter,myResponse.quran[i].verse,myResponse.quran[i].text))
//        }
//    }


}