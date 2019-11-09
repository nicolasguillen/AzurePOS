package ch.nicolasguillen.azurepos.extensions

import io.reactivex.disposables.Disposable

fun Disposable.disposedBy(bag: DisposeBag) {
    bag.addDisposable(this)
}