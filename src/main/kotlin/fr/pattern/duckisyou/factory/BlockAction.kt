package fr.pattern.duckisyou.factory

import fr.pattern.duckisyou.enums.EBehavior

class BlockAction(image: String, type: EBehavior): Block(image) {
    var type: EBehavior = type
}