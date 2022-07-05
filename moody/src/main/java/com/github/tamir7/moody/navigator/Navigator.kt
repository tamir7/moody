package com.github.tamir7.moody.navigator

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    private var containerId: Int = 0
    private val activitySubject: BehaviorSubject<AppCompatActivity> = BehaviorSubject.create()
    private val enabledSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

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
        source.take(1).subscribe { activity ->
            activity.onBackPressed()
        }
    }

    private fun navigate(screen: Screen, addToBackStack: Boolean = false) {
        source.take(1).subscribe { activity ->
            activity.supportFragmentManager.beginTransaction().apply {
                replace(containerId, screen.createFragment(), screen.javaClass.simpleName)
                if (addToBackStack) {
                    addToBackStack(screen.javaClass.simpleName)
                }
                commit()
            }
        }
    }
}
