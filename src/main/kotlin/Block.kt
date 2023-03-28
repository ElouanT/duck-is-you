import enums.*

class Block(type: EBlock, img: String, spriteType: ESprite? = null, behaviorType: EBehavior? = null): GameObject(img, EBehavior.PUSH) {
    var type: EBlock = type
    var spriteType: ESprite? = spriteType
    var behaviorType: EBehavior? = behaviorType
}