package gr.aytn.islamicapp.data

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Ayat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Ayat::class],version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ayatDao(): QuranDao

    class Callback @Inject constructor(private val resources: Resources,
                                       private val database: Provider<AppDatabase>): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().ayatDao()
            CoroutineScope(Dispatchers.IO).launch {
                fillQuranDatabase(dao)
            }
        }
        private fun fillQuranDatabase (dao: QuranDao){
            // using try catch to load the necessary data
            try {
                //creating variable that holds the loaded data
                val quran = loadJSONArray()
                Log.i("database", "${quran.length()}")
                for (i in 0 until quran.length()){
                    //variable to obtain the JSON object
                    val ayat = quran.getJSONObject(i)

                    //Using the JSON object to assign data
                    val chapter = ayat.getString("chapter").toInt()
                    val verse = ayat.getString("verse").toInt()
                    val text = ayat.getString("text")

                    //data loaded to the entity
                    val ayatEntity = Ayat(
                        chapter,verse,text
                    )

                    //using dao to insert data to the database
                    dao.addAyat(ayatEntity)
                }
            }
            //error when exception occurs
            catch (e: JSONException) {
                Log.i("fillWithStartingNotes", "$e")
            }
        }
        // loads JSON data
        private fun loadJSONArray(): JSONArray {
            //obtain input byte
            val inputStream = resources.openRawResource(R.raw.quran)
            //using Buffered reader to read the inputstream byte
            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }
    }
}