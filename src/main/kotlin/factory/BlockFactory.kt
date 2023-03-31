package factory

import enums.*

class BlockFactory {

    fun createBlock(): BlockIs {
        return BlockIs()
    }
    fun createBlock(type: ESprite): BlockSubject {
        return when(type) {
            ESprite.DUCK -> BlockSubject("block_duck.png", type)
            ESprite.FLAG -> BlockSubject("block_flag.png", type)
            ESprite.WALL -> BlockSubject("block_wall.png", type)
            ESprite.ROCK -> BlockSubject("block_rock.png", type)
            ESprite.LAVA -> BlockSubject("block_lava.png", type)
        }
    }

    fun createBlock(type: EBehavior): BlockAction {
        return when(type) {
            EBehavior.STOP -> BlockAction("block_stop.png", type)
            EBehavior.MOVE -> BlockAction("block_you.png", type)
            EBehavior.PUSH -> BlockAction("block_push.png", type)
            EBehavior.DEFEAT -> BlockAction("block_defeat.png", type)
            EBehavior.WIN -> BlockAction("block_win.png", type)
        }
    }
}