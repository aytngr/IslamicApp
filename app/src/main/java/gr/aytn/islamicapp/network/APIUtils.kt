package gr.aytn.islamicapp.network

class APIUtils {
    val BASE_URL:String = " http://api.aladhan.com/v1/"
    val BASE_URL_QURAN:String = "https://cdn.jsdelivr.net/gh/fawazahmed0/quran-api@1/editions/"

    fun getAPIService(): APIService?{
        return RetrofitClient().getClient(BASE_URL)?.create(APIService::class.java)
    }

    fun getAPIServiceForQuran(): APIService?{
        return RetrofitClient().getClient(BASE_URL_QURAN)?.create(APIService::class.java)
    }
}