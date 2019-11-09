package ch.nicolasguillen.azurepos

import android.app.Application
import ch.nicolasguillen.azurepos.di.ApplicationComponent
import ch.nicolasguillen.azurepos.di.DaggerApplicationComponent
import ch.nicolasguillen.azurepos.di.modules.ApplicationModule
import ch.nicolasguillen.azurepos.di.modules.UseCaseModule

class AzurePOSApp: Application() {

    companion object {
        @JvmStatic var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = initApplicationComponent()

    }

    private fun initApplicationComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .useCaseModule(UseCaseModule())
            .build()
    }

}