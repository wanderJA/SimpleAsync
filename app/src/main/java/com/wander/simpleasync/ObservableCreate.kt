package com.wander.simpleasync

class ObservableCreate<T> : ObservableAsync<T>() {
    override fun subscribeActual(observer: Observer<T>) {
    }
}