package gr.aytn.islamicapp.data

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.model.Ayat
import gr.aytn.islamicapp.model.Chapter
import gr.aytn.islamicapp.model.PrayerTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Ayat::class, Chapter::class,PrayerTime::class],version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ayatDao(): QuranDao
    abstract fun prayerDao(): PrayerDao

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
                val quran_alikhan_musayev = loadJSONArrayAzAlikhan()
                val quran_arabic = loadJSONArrayArabic()
                val chapters = loadJSONArrayChapters()
                for (i in 0 until chapters.length()){
                    val chapter = chapters.getJSONObject(i)

                    val number = chapter.getString("number").toInt()
                    val name = chapter.getString("name")
                    val classification = chapter.getString("classification")
                    val verses = chapter.getString("verses").toInt()

                    val chapterEntity = Chapter(
                        number,name,classification,verses
                    )

                    dao.addChapter(chapterEntity)
                }
                for (i in 0 until quran.length()){
                    //variable to obtain the JSON object
                    val ayat = quran.getJSONObject(i)
                    val ayat_alikhan_musayev = quran_alikhan_musayev.getJSONObject(i)
                    val ayat_arabic = quran_arabic.getJSONObject(i)

                    //Using the JSON object to assign data
                    val chapter = ayat.getString("chapter").toInt()
                    val verse = ayat.getString("verse").toInt()
                    val arabic = ayat_arabic.getString("text")
                    val text = ayat.getString("text")
                    val text_alikhan_musayev = ayat_alikhan_musayev.getString("text")

                    //data loaded to the entity
                    val ayatEntity = Ayat(
                        chapter,verse,arabic,text,text_alikhan_musayev
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
        private fun loadJSONArrayAzAlikhan(): JSONArray {
            //obtain input byte
            val inputStream = resources.openRawResource(R.raw.quran_alikhan_musayev)
            //using Buffered reader to read the inputstream byte
            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }
        private fun loadJSONArrayArabic(): JSONArray {
            //obtain input byte
            val inputStream = resources.openRawResource(R.raw.quran_arabic_simple)
            //using Buffered reader to read the inputstream byte
            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }
        private fun loadJSONArrayChapters(): JSONArray {
            //obtain input byte
            val inputStream = resources.openRawResource(R.raw.chapters)
            //using Buffered reader to read the inputstream byte
            BufferedReader(inputStream.reader()).use {
                return JSONArray(it.readText())
            }
        }
    }
}