package fr.pattern.duckisyou.observer

import fr.pattern.duckisyou.enums.EBehavior

class ConcretSubject(): ISubject {
    var observers: ArrayList<IObserver> = ArrayList()
    var behavior: EBehavior = EBehavior.STOP
    override fun addObserver(observer: IObserver) {
        observers.add(observer)
    }
    override fun removeObserver(observer: IObserver) {
        observers.remove(observer)
    }

    override fun notifyObservers() {
        for (obs in observers) {
            obs.updateBehavior(behavior)
        }
    }
}