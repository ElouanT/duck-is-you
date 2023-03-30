import enums.ESprite

class BlockSubject(image: String, type: ESprite): Block(image) {
    var type: ESprite = type
}