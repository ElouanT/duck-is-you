package factory

import GameObject
import enums.EBehavior

abstract class Block(image: String): GameObject(image, EBehavior.PUSH) {
}