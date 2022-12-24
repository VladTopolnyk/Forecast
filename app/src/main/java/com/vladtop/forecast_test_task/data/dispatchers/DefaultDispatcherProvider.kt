package com.vladtop.pet_project.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject

class DefaultDispatcherProvider @Inject constructor(): DispatcherProvider {
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val main: MainCoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}