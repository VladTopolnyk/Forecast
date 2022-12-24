package com.vladtop.pet_project.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val main: MainCoroutineDispatcher
    val io: CoroutineDispatcher
}