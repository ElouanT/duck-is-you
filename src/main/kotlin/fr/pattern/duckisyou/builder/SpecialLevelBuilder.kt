package fr.pattern.duckisyou.builder

import fr.pattern.duckisyou.*
import fr.pattern.duckisyou.observer.*
import fr.pattern.duckisyou.enums.*

class SpecialLevelBuilder: ILevelBuilder {
    var name = ""
    var backgroundColor = "#15181F"
    var map = Map(mapWidth, mapHeight, scaleFactor)
    override fun buildParameters(name: String, backgroundColor: String) {
        this.name = name
        if (backgroundColor != "") this.backgroundColor = backgroundColor
    }

    override fun buildMap(mapString: String) {
        for (i in 0 until mapString.length) {
            when (mapString[i]) {
                // Sprites
                'd' -> map.setGameObject(Sprite(ESprite.DUCK, "sprite_duck.png", EBehavior.MOVE), i)
                'f' -> map.setGameObject(Sprite(ESprite.FLAG, "sprite_flag.png", EBehavior.WIN), i)
                'w' -> map.setGameObject(Sprite(ESprite.WALL, "sprite_wall_base.png"), i)
                'k' -> map.setGameObject(Sprite(ESprite.ROCK, "keys.png", EBehavior.STOP), i)
                't' -> map.setGameObject(Sprite(ESprite.ROCK, "thanks.png", EBehavior.STOP), i)
            }
        }
    }

    override fun getLevel(): Level {
        return Level(name, backgroundColor, map)
    }
}