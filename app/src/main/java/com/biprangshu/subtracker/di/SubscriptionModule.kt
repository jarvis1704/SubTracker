package com.biprangshu.subtracker.di

import com.biprangshu.subtracker.data.repository.SubscriptionRepositoryImpl
import com.biprangshu.subtracker.domain.repository.SubscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SubscriptionModule {

    @Binds
    @Singleton
    abstract fun bindsSubscriptionRepository(
        impl: SubscriptionRepositoryImpl
    ): SubscriptionRepository
}