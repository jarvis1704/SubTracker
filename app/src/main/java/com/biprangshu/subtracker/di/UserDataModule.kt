package com.biprangshu.subtracker.di

import com.biprangshu.subtracker.data.local.repository.UserRepositoryImpl
import com.biprangshu.subtracker.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun bindsUserDataRepository(
        impl: UserRepositoryImpl
    ): UserDataRepository
}