package ch.nicolasguillen.azurepos.usecases

import ch.nicolasguillen.azurepos.services.LoggerService
import ch.nicolasguillen.azurepos.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class FelFelCheckHealthUseCaseTest {

    @Mock
    private lateinit var mockLoadTemperatureUseCase: LoadTemperatureUseCase

    @Mock
    private lateinit var mockLoadBandwidthUseCase: LoadBandwidthUseCase

    @Mock
    private lateinit var mockLoggerService: LoggerService

    private lateinit var testee: CheckHealthUseCase

    private val scheduler = TestScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setComputationSchedulerHandler { scheduler }

        testee = FelFelCheckHealthUseCase(mockLoadTemperatureUseCase, mockLoadBandwidthUseCase, mockLoggerService)
    }

    @Test
    fun test_startHealthCheck_then_getTemperatureAndGetBandwidth() {
        //Arrange
        Mockito.doReturn(Single.just(10.0))
            .whenever(mockLoadTemperatureUseCase).getTemperature()
        Mockito.doReturn(Single.just(1000L))
            .whenever(mockLoadBandwidthUseCase).getBandwidth()
        Mockito.doReturn(Single.just(Unit))
            .whenever(mockLoggerService).log(anyDouble(), anyLong())

        //Act
        testee.startHealthCheck()

        //Assert
        Mockito.verify(mockLoadTemperatureUseCase).getTemperature()
        Mockito.verify(mockLoadBandwidthUseCase).getBandwidth()
    }

    @Test
    fun test_startHealthCheck_when_didLoadAllInformation_then_logThem() {
        //Arrange
        Mockito.doReturn(Single.just(10.0))
            .whenever(mockLoadTemperatureUseCase).getTemperature()
        Mockito.doReturn(Single.just(1000L))
            .whenever(mockLoadBandwidthUseCase).getBandwidth()
        Mockito.doReturn(Single.just(Unit))
            .whenever(mockLoggerService).log(anyDouble(), anyLong())

        //Act
        testee.startHealthCheck()

        //Assert
        Mockito.verify(mockLoggerService).log(anyDouble(), anyLong())
    }

    @Test
    fun test_startHealthCheck_when_didLogInformationAndWait5Minutes_then_loadInformationAgain() {
        //Arrange
        Mockito.doReturn(Single.just(10.0))
            .whenever(mockLoadTemperatureUseCase).getTemperature()
        Mockito.doReturn(Single.just(1000L))
            .whenever(mockLoadBandwidthUseCase).getBandwidth()
        Mockito.doReturn(Single.just(Unit))
            .whenever(mockLoggerService).log(anyDouble(), anyLong())

        //Act
        testee.startHealthCheck()
        scheduler.advanceTimeBy(5, TimeUnit.MINUTES)

        //Assert
        Mockito.verify(mockLoadTemperatureUseCase, times(2)).getTemperature()
        Mockito.verify(mockLoadBandwidthUseCase, times(2)).getBandwidth()
    }

    @Test
    fun test_startHealthCheck_when_didFailFirstLoadOfTemperature_then_waitAndTryAgain() {
        //Arrange
        whenever(mockLoadTemperatureUseCase.getTemperature())
            .thenReturn(Single.error(Exception()))
            .thenReturn(Single.just(10.0))
        Mockito.doReturn(Single.just(1000L))
            .whenever(mockLoadBandwidthUseCase).getBandwidth()
        Mockito.doReturn(Single.just(Unit))
            .whenever(mockLoggerService).log(anyDouble(), anyLong())

        //Act
        testee.startHealthCheck()
        scheduler.advanceTimeBy(5, TimeUnit.MINUTES)

        //Assert
        Mockito.verify(mockLoadTemperatureUseCase, times(2)).getTemperature()
        Mockito.verify(mockLoadBandwidthUseCase, times(2)).getBandwidth()
    }

    @Test
    fun test_startHealthCheck_when_didFailToLog_then_waitAndTryAgain() {
        //Arrange
        Mockito.doReturn(Single.just(10.0))
            .whenever(mockLoadTemperatureUseCase).getTemperature()
        Mockito.doReturn(Single.just(1000L))
            .whenever(mockLoadBandwidthUseCase).getBandwidth()
        Mockito.doReturn(Single.error<Exception>(Exception()))
            .whenever(mockLoggerService).log(anyDouble(), anyLong())

        //Act
        testee.startHealthCheck()
        scheduler.advanceTimeBy(5, TimeUnit.MINUTES)

        //Assert
        Mockito.verify(mockLoggerService, times(2)).log(anyDouble(), anyLong())
    }

}