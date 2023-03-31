package fr.pattern.duckisyou.factory

import fr.pattern.duckisyou.GameObject
import fr.pattern.duckisyou.enums.EBehavior

abstract class Block(image: String): GameObject(image, EBehavior.PUSH) {
}