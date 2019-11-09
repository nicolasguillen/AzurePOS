package ch.nicolasguillen.azurepos.usecases

import ch.nicolasguillen.azurepos.extensions.DisposeBag
import ch.nicolasguillen.azurepos.extensions.disposedBy
import ch.nicolasguillen.azurepos.services.LoggerService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

interface CheckHealthUseCase {

    fun startHealthCheck()

}

class FelFelCheckHealthUseCase(private val loadTemperatureUseCase: LoadTemperatureUseCase,
                               private val loadBandwidthUseCase: LoadBandwidthUseCase,
                               private val loggerService: LoggerService): CheckHealthUseCase {

    private val disposeBag = DisposeBag()

    private val startHealthCheck = PublishSubject.create<Unit>()
    private val startTimer = PublishSubject.create<Unit>()

    init {
        startTimer
            .switchMap {
                Observable.timer(5, TimeUnit.MINUTES)
            }
            .subscribeBy(
                onNext = {
                    startHealthCheck.onNext(Unit)
                }
            ).disposedBy(this.disposeBag)


        startHealthCheck
            .switchMapSingle {
                this.performAndLogHealthCheck()
            }
            .subscribeBy(
                onNext = {
                    startTimer.onNext(Unit)
                }
            ).disposedBy(this.disposeBag)

    }

    private fun performAndLogHealthCheck(): Single<Unit> {
        return Single.create { observer ->
            Single.zip(loadTemperatureUseCase.getTemperature(), loadBandwidthUseCase.getBandwidth(), BiFunction {
                    temperature: Double, bandwidth: Long -> temperature to bandwidth
            })
                .flatMap { (temperature, bandwidth) ->
                    this.loggerService.log(temperature, bandwidth)
                }
                .subscribe(
                    { observer.onSuccess(Unit) },
                    { observer.onSuccess(Unit) }
                )
        }
    }

    override fun startHealthCheck() = startHealthCheck.onNext(Unit)

}