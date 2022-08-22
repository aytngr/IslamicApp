package gr.aytn.islamicapp.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import gr.aytn.islamicapp.data.AppDatabase
import gr.aytn.islamicapp.data.PrayerDao
import gr.aytn.islamicapp.data.QuranDao
import gr.aytn.islamicapp.repository.PrayerRepository
import gr.aytn.islamicapp.repository.QuranRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application,callback: AppDatabase.Callback)
    = Room.databaseBuilder(app, AppDatabase::class.java,"quran_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()
    
    @Provides
    fun provideAyatDao(db: AppDatabase) = db.ayatDao()

    @Provides
    fun providePrayerDao(db: AppDatabase) = db.prayerDao()

    @Provides
    @Singleton
    fun resourcesProvider(@ApplicationContext context: Context): Resources = context.resources

    @Provides
    fun provideQuranRepository(dao: QuranDao) = QuranRepository(dao)

    @Provides
    fun providePrayerRepository(dao: PrayerDao) = PrayerRepository(dao)
}