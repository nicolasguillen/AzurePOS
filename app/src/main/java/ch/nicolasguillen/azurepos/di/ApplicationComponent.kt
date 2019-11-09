package ch.nicolasguillen.azurepos.di

import ch.nicolasguillen.azurepos.di.modules.ApplicationModule
import ch.nicolasguillen.azurepos.di.modules.ModelModule
import ch.nicolasguillen.azurepos.di.modules.UseCaseModule
import ch.nicolasguillen.azurepos.ui.activities.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ModelModule::class, UseCaseModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}