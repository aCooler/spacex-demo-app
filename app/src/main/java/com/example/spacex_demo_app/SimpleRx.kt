package com.example.spacex_demo_app

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.*
object SimpleRx {

    var bag = CompositeDisposable()

    fun simpleValues() {
        println("~~~~~~simpleValues~~~~~~")

        val someInfo = BehaviorSubject.createDefault("1")
        println("🙈 someInfo.value ${someInfo.value}")

        val plainString = someInfo.value
        println("🙈 plainString: $plainString")

        //someInfo.accept("2")
        println("🙈 someInfo.value ${someInfo.value}")

        someInfo.subscribe { newValue ->
            println("🦄 value has changed: $newValue")
        }

        //someInfo.accept("3")

    }

    fun subjects() {
        val behaviorSubject = BehaviorSubject.createDefault(24)

//        val disposable = behaviorSubject.subscribe({ newValue ->
//            //onNext
//            println("🕺 behaviorSubject subscription: $newValue")
//        }, { error ->
//            //onError
//            println("🕺 error: ${error.localizedMessage}")
//        }, {
//            //onCompleted
//            println("🕺 completed")
//        }, { disposable ->
//            //onSubscribed
//            println("🕺 subscribed")
//        })

        val disposable = behaviorSubject.subscribe(
            { newValue ->
            //onNext
            println("🕺 behaviorSubject subscription: $newValue")
        }, { error ->
            //onError
            println("🕺 error: ${error.localizedMessage}")
        }

        )
        behaviorSubject.onNext(34)
        behaviorSubject.onNext(48)
        behaviorSubject.onNext(48)

//
//         val someException= IllegalArgumentException("some fake error")
//         behaviorSubject.onError(someException)
//         behaviorSubject.onNext(109)


        behaviorSubject.onComplete()
        behaviorSubject.onNext(10923)
    }

    fun basicObservable() {
        val observable = Observable.create<String> { observer ->

            println("🍄 ~~~Observable logic being triggered ~~~")


            runBlocking {
                launch {
                    delay(1000)
                    observer.onNext("some value 23")
                    observer.onComplete()

                }

            }


        }

        observable.subscribe { someString ->
            println("🍄 new value $someString")

        }

        val observer = observable.subscribe { someString ->
            println("🍄 another subscriber $someString")
        }

        bag.add(observer)
    }

    fun creatingObservables() {
         //val observable =Observable.just(23)
        //val observable =Observable.interval(300,TimeUnit.MILLISECONDS).timeInterval(AndroidSchedulers.mainThread())
        val userIds = arrayOf(1, 2, 3, 4, 5)
        val observable = Observable.fromArray(*userIds)

        //userIds.toObservable()

    }


}










