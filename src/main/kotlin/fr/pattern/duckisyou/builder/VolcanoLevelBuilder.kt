package fr.pattern.duckisyou.builder

import fr.pattern.duckisyou.mapHeight
import fr.pattern.duckisyou.mapWidth
import fr.pattern.duckisyou.scaleFactor

import fr.pattern.duckisyou.*
import fr.pattern.duckisyou.observer.*
import fr.pattern.duckisyou.factory.*
import fr.pattern.duckisyou.enums.*

class VolcanoLevelBuilder: ILevelBuilder {
    var name = ""
    var backgroundColor = "#251D1B"
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
                'w' -> map.setGameObject(Sprite(ESprite.WALL, "sprite_wall_volcano.png"), i)
                'r' -> map.setGameObject(Sprite(ESprite.ROCK, "sprite_rock_volcano.png"), i)
                'l' -> map.setGameObject(Sprite(ESprite.LAVA, "sprite_lava.png"), i)
                // Blocks
                'I' -> map.setGameObject(factory.createBlock(), i)
                'D' -> map.setGameObject(factory.createBlock(ESprite.DUCK), i)
                'F' -> map.setGameObject(factory.createBlock(ESprite.FLAG), i)
                'W' -> map.setGameObject(factory.createBlock(ESprite.WALL), i)
                'R' -> map.setGameObject(factory.createBlock(ESprite.ROCK), i)
                'L' -> map.setGameObject(factory.createBlock(ESprite.LAVA), i)
                'Y' -> map.setGameObject(factory.createBlock(EBehavior.MOVE), i)
                'P' -> map.setGameObject(factory.createBlock(EBehavior.PUSH), i)
                'X' -> map.setGameObject(factory.createBlock(EBehavior.DEFEAT), i)
                'V' -> map.setGameObject(factory.createBlock(EBehavior.WIN), i)
            }
        }
    }

    override fun getLevel(): Level {
        return Level(name, backgroundColor, map)
    }
}