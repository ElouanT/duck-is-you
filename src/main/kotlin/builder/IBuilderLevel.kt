package builder

import Level

interface IBuilderLevel {

    fun buildDuck()
    fun buildFlag()
    fun buildSprites()
    fun buildBlocks()
    fun getLevel(): Level
}