package com.biprangshu.subtracker.di

import android.app.Application
import androidx.room.Room
import com.biprangshu.subtracker.data.local.database.AppDatabase
import com.biprangshu.subtracker.data.local.database.ForecastDatabase
import com.biprangshu.subtracker.data.local.dao.InsightDao
import com.biprangshu.subtracker.data.local.database.InsightDatabase
import com.biprangshu.subtracker.data.local.dao.PriceAlertDao
import com.biprangshu.subtracker.data.local.database.PriceAlertDatabase
import com.biprangshu.subtracker.data.local.dao.SubscriptionDao
import com.biprangshu.subtracker.data.local.dao.UserDataDao
import com.biprangshu.subtracker.data.local.database.UserDatabase
import com.biprangshu.subtracker.data.local.dao.ForecastDao
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
    fun providePriceIncreaseDatabase(app: Application): PriceAlertDatabase{
        return Room.databaseBuilder(
            app,
            PriceAlertDatabase::class.java,
            "price_alert_db"
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
    fun provideForecastDao(db: ForecastDatabase): ForecastDao {
        return db.forecastDAO()
    }

    @Provides
    @Singleton
    fun providePriceAlertDao(db: PriceAlertDatabase): PriceAlertDao{
        return db.priceAlertDao()
    }


}