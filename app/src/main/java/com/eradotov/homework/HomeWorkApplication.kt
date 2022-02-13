package com.eradotov.homework

import android.app.Application

class HomeWorkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}