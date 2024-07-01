package me.joaomanaia.gobblet

import android.app.Application
import di.KoinStarter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class GobbletApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinStarter.init {
            androidLogger()
            androidContext(this@GobbletApplication)
        }
    }
}
