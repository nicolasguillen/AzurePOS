package ch.nicolasguillen.azurepos.viewmodels

import ch.nicolasguillen.azurepos.extensions.disposedBy
import ch.nicolasguillen.azurepos.usecases.CheckHealthUseCase
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface MainViewModelInputs {
    fun onCreate()
}

interface MainViewModelOutputs {

}

class MainViewModel @Inject constructor(private val checkHealthUseCase: CheckHealthUseCase): DisposableViewModel(), MainViewModelInputs, MainViewModelOutputs {

    //INPUTS
    private val onCreate = PublishSubject.create<Unit>()

    val inputs: MainViewModelInputs = this
    val outputs: MainViewModelOutputs = this

    init {
        onCreate
            .subscribeBy(
                onNext = {
                    this.checkHealthUseCase.startHealthCheck()
                }
            ).disposedBy(disposeBag)
    }

    override fun onCreate() = this.onCreate.onNext(Unit)
}