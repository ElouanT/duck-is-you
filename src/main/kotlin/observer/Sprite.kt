package observer

import GameObject
import enums.EBehavior
import enums.ESprite

class Sprite(type: ESprite, img: String, behavior: EBehavior = EBehavior.STOP) : GameObject(img, behavior), IObserver {
    var type = type
    override var behavior = behavior
    override fun updateBehavior(behavior: EBehavior) {
        this.behavior = behavior
    }
}