package gr.aytn.islamicapp.network

class APIUtils {
    val BASE_URL:String = " http://api.aladhan.com/v1/"

    fun getAPIService(): APIService?{
        return RetrofitClient().getClient(BASE_URL)?.create(APIService::class.java)
    }
}