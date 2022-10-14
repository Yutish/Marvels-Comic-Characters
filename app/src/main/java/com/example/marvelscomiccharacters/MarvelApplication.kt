package com.example.marvelscomiccharacters

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MarvelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MarvelApplication
    }
}