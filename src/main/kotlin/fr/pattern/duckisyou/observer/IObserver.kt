package fr.pattern.duckisyou.observer

import fr.pattern.duckisyou.enums.EBehavior

interface IObserver {
    fun updateBehavior(behavior: EBehavior) {}
}