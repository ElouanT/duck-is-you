package observer

interface ISubject {
    fun addObserver(observer: IObserver) {}
    fun removeObserver(observer: IObserver) {}

    fun notifyObservers() {}
}