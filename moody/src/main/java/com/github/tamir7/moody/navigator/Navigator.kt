package com.github.tamir7.moody.navigator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator  {
    private var containerId: Int = 0
    private val activitySubject: BehaviorSubject<AppCompatActivity> = BehaviorSubject.create()
    private val enabledSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()
    private var navigationDisposable: Disposable? = null
    private var backDisposable: Disposable? = null

    @Inject constructor() {
        Timber.e("creating object")
    }

    private val source = Observables
        .combineLatest(enabledSubject.hide(), activitySubject.hide())
        .filter { (enabled, _) -> enabled  }
        .map { (_, activity) -> activity }

    fun start(activity: AppCompatActivity, @IdRes containerId: Int) {
        this.containerId = containerId
        activitySubject.onNext(activity)
        enabledSubject.onNext(true)
    }

    fun stop() {
        enabledSubject.onNext(false)
    }

    fun setRoot(screen: Screen) = navigate(screen)

    fun add(screen: Screen) = navigate(screen, true)

    fun replace(screen: Screen) = navigate(screen, false)

    fun goBack() {
        backDisposable = source.subscribe { activity ->
            activity.onBackPressed()
            backDisposable?.dispose()
        }
    }

    private fun navigate(screen: Screen, addToBackStack: Boolean = false) {
        navigationDisposable = source.subscribe { activity ->
            activity.supportFragmentManager.beginTransaction().apply {
                replace(containerId, screen.createFragment(), screen.javaClass.simpleName)
                if (addToBackStack) {
                    addToBackStack(screen.javaClass.simpleName)
                }
                commit()
                navigationDisposable?.dispose()
            }
        }
    }
}
