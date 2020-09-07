package io.pootis.androidlabtsu.dagger

import dagger.Component
import io.pootis.androidlabtsu.service.NetworkModule
import io.pootis.androidlabtsu.ui.MainActivity

@Component(modules = [AppModule::class,
    NetworkModule::class,MainActivityModule::class,ViewModelModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
}