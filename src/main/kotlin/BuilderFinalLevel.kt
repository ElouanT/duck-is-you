class BuilderFinalLevel: IBuilderLevel {
    var number = 1
    var map = Map(mapWidth, mapHeight, scaleFactor)
    var duckCoordinate = Pair(mapWidth/2, mapHeight/2)
    override fun buildDuck() {
        map.setGameObject(Sprite(ESprite.DUCK, "sprite_duck.png", EBehavior.MOVE), duckCoordinate.first, duckCoordinate.second)
    }
    override fun buildFlag() {}
    override fun buildSprites() {
        for (i in 0 until mapWidth) {
            map.setGameObject(Sprite(ESprite.FLAG, "sprite_flag.png"), i, 0)
        }
        for (i in 0 until mapWidth) {
            map.setGameObject(Sprite(ESprite.FLAG, "sprite_flag.png"), i, mapHeight-1)
        }
    }
    override fun buildBlocks() {

    }
    override fun getLevel(): Level {
        return Level(number, map)
    }
}