package fr.pattern.duckisyou.observer

interface ISubject {
    fun addObserver(observer: IObserver) {}
    fun removeObserver(observer: IObserver) {}

    fun notifyObservers() {}
}