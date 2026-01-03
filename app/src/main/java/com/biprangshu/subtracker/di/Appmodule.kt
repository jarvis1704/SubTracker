package com.biprangshu.subtracker.di

import android.app.Application
import androidx.room.Room
import com.biprangshu.subtracker.data.local.AppDatabase
import com.biprangshu.subtracker.data.local.SubscriptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "subtracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSubscriptionDao(db: AppDatabase): SubscriptionDao{
        return db.subscriptionDAO()
    }

}