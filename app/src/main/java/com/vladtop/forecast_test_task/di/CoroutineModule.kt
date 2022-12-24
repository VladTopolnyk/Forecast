package com.vladtop.forecast_test_task.di

import com.vladtop.pet_project.data.dispatchers.DefaultDispatcherProvider
import com.vladtop.pet_project.data.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineModule {

    @Binds
    abstract fun bindDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider
}
