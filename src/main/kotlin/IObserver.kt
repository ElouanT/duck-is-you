import enums.EBehavior

interface IObserver {
    fun updateBehavior(behavior: EBehavior) {}
}