package io.pootis.androidlabtsu.dagger

import android.app.Application

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: MyApplication): AppComponent =
        DaggerAppComponent.builder()
            .build()
}