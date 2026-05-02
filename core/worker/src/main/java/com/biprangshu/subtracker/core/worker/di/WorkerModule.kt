package com.biprangshu.subtracker.core.worker.di

import com.biprangshu.subtracker.core.domain.repository.ReminderSchedulerContract
import com.biprangshu.subtracker.core.worker.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    @Singleton
    abstract fun bindsReminderScheduler(
        impl: ReminderScheduler
    ): ReminderSchedulerContract
}
