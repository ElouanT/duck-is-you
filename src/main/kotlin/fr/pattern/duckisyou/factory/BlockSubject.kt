package fr.pattern.duckisyou.factory

import fr.pattern.duckisyou.enums.ESprite

class BlockSubject(image: String, type: ESprite): Block(image) {
    var type: ESprite = type
}