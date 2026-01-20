package com.biprangshu.subtracker.di

import android.app.Application
import androidx.room.Room
import com.biprangshu.subtracker.data.local.AppDatabase
import com.biprangshu.subtracker.data.local.ForecastDatabase
import com.biprangshu.subtracker.data.local.InsightDao
import com.biprangshu.subtracker.data.local.InsightDatabase
import com.biprangshu.subtracker.data.local.SubscriptionDao
import com.biprangshu.subtracker.data.local.UserDataDao
import com.biprangshu.subtracker.data.local.UserDatabase
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
    fun providesUserDataDatabase(app: Application): UserDatabase{
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesInsightDatabase(app: Application): InsightDatabase{
        return Room.databaseBuilder(
            app,
            InsightDatabase::class.java,
            "insight_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesForecastDatabase(app: Application): ForecastDatabase{
        return Room.databaseBuilder(
            app,
            ForecastDatabase::class.java,
            "forecast_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSubscriptionDao(db: AppDatabase): SubscriptionDao{
        return db.subscriptionDAO()
    }

    @Provides
    @Singleton
    fun provideUserDatabaseDao(db: UserDatabase): UserDataDao{
        return db.userDataDAO()
    }

    @Provides
    @Singleton
    fun provideInsightDao(db: InsightDatabase): InsightDao {
        return db.insightDAO()
    }

    @Provides
    @Singleton
    fun provideForecastDao(db: ForecastDatabase): com.biprangshu.subtracker.data.local.ForecastDao {
        return db.forecastDAO()
    }


}