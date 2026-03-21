package com.biprangshu.subtracker.core.data.di

import com.biprangshu.subtracker.core.data.local.repository.SubscriptionRepositoryImpl
import com.biprangshu.subtracker.core.data.local.repository.UserPreferencesRepositoryImpl
import com.biprangshu.subtracker.core.data.local.repository.UserRepositoryImpl
import com.biprangshu.subtracker.core.domain.repository.SubscriptionRepository
import com.biprangshu.subtracker.core.domain.repository.UserDataRepository
import com.biprangshu.subtracker.core.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsSubscriptionRepository(
        impl: SubscriptionRepositoryImpl
    ): SubscriptionRepository

    @Binds
    @Singleton
    abstract fun bindsUserDataRepository(
        impl: UserRepositoryImpl
    ): UserDataRepository

    @Binds
    @Singleton
    abstract fun bindsUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
}
