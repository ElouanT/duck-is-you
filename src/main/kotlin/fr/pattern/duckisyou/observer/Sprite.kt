package fr.pattern.duckisyou.observer

import fr.pattern.duckisyou.GameObject
import fr.pattern.duckisyou.enums.*

class Sprite(type: ESprite, img: String, behavior: EBehavior = EBehavior.STOP) : GameObject(img, behavior), IObserver {
    var type = type
    override var behavior = behavior
    override fun updateBehavior(behavior: EBehavior) {
        this.behavior = behavior
    }
}