package io.pootis.androidlabtsu.dagger

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.pootis.androidlabtsu.data.GameRepository
import io.pootis.androidlabtsu.data.GameViewModelFactory

@Module
@Suppress("unused")
object MainActivityModule {

    @JvmStatic
    @Provides
    internal fun providesMainViewModelFactory(gameRepository: GameRepository)
            : GameViewModelFactory {
        return GameViewModelFactory(gameRepository)
    }
}