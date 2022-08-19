package gr.aytn.islamicapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.aytn.islamicapp.model.Ayat

@Database(entities = [Ayat::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ayatDao(): QuranDao
}