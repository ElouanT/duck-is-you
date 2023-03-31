package fr.pattern.duckisyou.builder

import fr.pattern.duckisyou.*
import fr.pattern.duckisyou.observer.*
import fr.pattern.duckisyou.factory.*
import fr.pattern.duckisyou.enums.*

class BaseLevelBuilder: ILevelBuilder {
    var name = ""
    var backgroundColor = "#15181F"
    var map = Map(mapWidth, mapHeight, scaleFactor)
    override fun buildParameters(name: String, backgroundColor: String) {
        this.name = name
        if (backgroundColor != "") this.backgroundColor = backgroundColor
    }

    override fun buildMap(mapString: String) {
        var factory = BlockFactory()
        for (i in 0 until mapString.length) {
            when (mapString[i]) {
                // Sprites
                'd' -> map.setGameObject(Sprite(ESprite.DUCK, "sprite_duck.png"), i)
                'f' -> map.setGameObject(Sprite(ESprite.FLAG, "sprite_flag.png"), i)
                'w' -> map.setGameObject(Sprite(ESprite.WALL, "sprite_wall_base.png"), i)
                'r' -> map.setGameObject(Sprite(ESprite.ROCK, "sprite_rock_base.png"), i)
                'h' -> map.setGameObject(Sprite(ESprite.FIRE, "sprite_fire.png"), i)
                // Blocks
                'I' -> map.setGameObject(factory.createBlock(), i)
                'D' -> map.setGameObject(factory.createBlock(ESprite.DUCK), i)
                'F' -> map.setGameObject(factory.createBlock(ESprite.FLAG), i)
                'W' -> map.setGameObject(factory.createBlock(ESprite.WALL), i)
                'R' -> map.setGameObject(factory.createBlock(ESprite.ROCK), i)
                'H' -> map.setGameObject(factory.createBlock(ESprite.FIRE), i)
                'Y' -> map.setGameObject(factory.createBlock(EBehavior.MOVE), i)
                'P' -> map.setGameObject(factory.createBlock(EBehavior.PUSH), i)
                'L' -> map.setGameObject(factory.createBlock(EBehavior.DEFEAT), i)
                'V' -> map.setGameObject(factory.createBlock(EBehavior.WIN), i)
            }
        }
    }

    override fun getLevel(): Level {
        return Level(name, backgroundColor, map)
    }
}