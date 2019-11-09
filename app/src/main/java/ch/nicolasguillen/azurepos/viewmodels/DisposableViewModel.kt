package ch.nicolasguillen.azurepos.viewmodels

import androidx.lifecycle.ViewModel
import ch.nicolasguillen.azurepos.extensions.DisposeBag

open class DisposableViewModel: ViewModel() {

    val disposeBag = DisposeBag()

    public override fun onCleared() {
        disposeBag.clearBag()
        super.onCleared()
    }

}