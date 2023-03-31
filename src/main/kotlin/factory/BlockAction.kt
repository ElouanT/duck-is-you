package factory

import enums.EBehavior

class BlockAction(image: String, type: EBehavior): Block(image) {
    var type: EBehavior = type
}