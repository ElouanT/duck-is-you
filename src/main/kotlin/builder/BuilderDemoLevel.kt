package builder

import enums.*
import Level
import Map
import observer.Sprite
import Block
import mapHeight
import mapWidth
import scaleFactor

class BuilderDemoLevel: IBuilderLevel {
    var number = 0
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
        map.setGameObject(Block(EBlock.SUBJECT, "block_duck.png", spriteType = ESprite.DUCK), 1, 0)
        map.setGameObject(Block(EBlock.IS, "block_is.png"), 2, 0)
        map.setGameObject(Block(EBlock.ACTION, "block_you.png", behaviorType = EBehavior.MOVE), 3, 0)

        // Flag
        map.setGameObject(Block(EBlock.SUBJECT, "block_flag.png", spriteType = ESprite.FLAG), 9, 0)
        map.setGameObject(Block(EBlock.IS, "block_is.png"), 10, 0)
        map.setGameObject(Block(EBlock.ACTION, "block_win.png", behaviorType = EBehavior.WIN), 11, 0)

        // Others
        map.setGameObject(Block(EBlock.IS, "block_is.png"), 3, 7)
        map.setGameObject(Block(EBlock.SUBJECT, "block_rock.png", spriteType = ESprite.ROCK), 2, 7)
        map.setGameObject(Block(EBlock.ACTION, "block_push.png", behaviorType = EBehavior.PUSH), 4, 6)

        // Behaviors
        map.checkForBehaviorChange()
    }
    override fun getLevel(): Level {
        return Level(number, map)
    }
}