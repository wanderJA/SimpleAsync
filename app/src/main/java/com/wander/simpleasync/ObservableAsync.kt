package com.wander.simpleasync

import java.util.concurrent.Callable

abstract class ObservableAsync<T> {

    fun create(runnable: Runnable){

    }

    fun <T> create(callable: Callable<T>){


    }




    fun subscribeOn(observer: Observer<T>){


    }

    abstract fun subscribeActual(observer: Observer<T>)


}