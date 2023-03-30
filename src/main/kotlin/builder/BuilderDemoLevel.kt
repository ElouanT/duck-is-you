package builder

import enums.*
import Level
import Map
import observer.Sprite
import BlockIs
import BlockSubject
import BlockAction

import mapHeight
import mapWidth
import scaleFactor

class BuilderDemoLevel: IBuilderLevel {
    var number = 0
    var backgroundColor = "#15181F"
    var map = Map(mapWidth, mapHeight, scaleFactor)
    var duckCoordinate = Pair(1, 4)
    var flagCoordinate = Pair(10, 4)
    override fun buildDuck() {
        map.setGameObject(Sprite(ESprite.DUCK, "sprite_duck.png"), duckCoordinate.first, duckCoordinate.second)
    }
    override fun buildFlag() {
        map.setGameObject(Sprite(ESprite.FLAG, "sprite_flag.png"), flagCoordinate.first, flagCoordinate.second)
    }
    override fun buildSprites() {
        // Ajout des murs
        for (i in 0..mapHeight -1) {
            map.setGameObject(Sprite(ESprite.WALL, "sprite_wall.png"), 6, i)
        }
        // Ajout des roches
        map.setGameObject(Sprite(ESprite.ROCK, "sprite_rock.png"), 6, 4)
    }
    override fun buildBlocks() {
        // Duck
        map.setGameObject(BlockSubject("block_duck.png", ESprite.DUCK), 1, 0)
        map.setGameObject(BlockIs(), 2, 0)
        map.setGameObject(BlockAction("block_you.png", EBehavior.MOVE), 3, 0)

        // Flag
        map.setGameObject(BlockSubject("block_flag.png", ESprite.FLAG), 9, 0)
        map.setGameObject(BlockIs(), 10, 0)
        map.setGameObject(BlockAction("block_win.png", EBehavior.WIN), 11, 0)

        // Others
        map.setGameObject(BlockIs(), 3, 7)
        map.setGameObject(BlockSubject("block_rock.png", ESprite.ROCK), 2, 7)
        map.setGameObject(BlockAction("block_push.png", EBehavior.PUSH), 4, 6)
    }
    override fun getLevel(): Level {
        buildDuck()
        buildFlag()
        buildSprites()
        buildBlocks()
        return Level(number, backgroundColor, map)
    }
}