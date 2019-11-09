package ch.nicolasguillen.azurepos.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ch.nicolasguillen.azurepos.AzurePOSApp
import ch.nicolasguillen.azurepos.R
import ch.nicolasguillen.azurepos.viewmodels.MainViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AzurePOSApp.applicationComponent?.inject(this)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.outputs.isCheckRunning()
            .observeOn(AndroidSchedulers.mainThread())
            .crashingSubscribe { isCheckRunning ->
                healthCheckStatus.text = if(isCheckRunning) "RUNNING" else "STOPPED"
            }

        viewModel.inputs.onCreate()

    }

    private fun <I> Observable<I>.crashingSubscribe(onNext: (I) -> Unit) {
        subscribe(onNext) { throw OnErrorNotImplementedException(it) }.addTo(disposables)
    }
}
