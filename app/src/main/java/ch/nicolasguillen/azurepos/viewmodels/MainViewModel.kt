package ch.nicolasguillen.azurepos.viewmodels

import ch.nicolasguillen.azurepos.extensions.disposedBy
import ch.nicolasguillen.azurepos.usecases.CheckHealthUseCase
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface MainViewModelInputs {
    fun onCreate()
}

interface MainViewModelOutputs {
    fun isCheckRunning(): Observable<Boolean>
}

class MainViewModel @Inject constructor(private val checkHealthUseCase: CheckHealthUseCase): DisposableViewModel(), MainViewModelInputs, MainViewModelOutputs {

    //INPUTS
    private val onCreate = PublishSubject.create<Unit>()

    //OUTPUTS
    private val isCheckRunning = PublishSubject.create<Boolean>()

    val inputs: MainViewModelInputs = this
    val outputs: MainViewModelOutputs = this

    init {
        onCreate
            .switchMap {
                this.checkHealthUseCase.isRunning()
            }
            .subscribeBy(
                onNext = { isRunning ->
                    isCheckRunning.onNext(isRunning)
                }
            ).disposedBy(disposeBag)
    }

    override fun onCreate() = this.onCreate.onNext(Unit)
    override fun isCheckRunning(): Observable<Boolean> = isCheckRunning
}