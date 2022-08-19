package gr.aytn.islamicapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gr.aytn.islamicapp.data.AppDatabase
import gr.aytn.islamicapp.data.QuranDao
import gr.aytn.islamicapp.repository.QuranRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(app, AppDatabase::class.java,"quran_database")
        .fallbackToDestructiveMigration()
        .build()
    
    @Provides
    fun providAyatDao(db: AppDatabase) = db.ayatDao()

    @Provides
    fun provideRepository(dao: QuranDao) = QuranRepository(dao)
}