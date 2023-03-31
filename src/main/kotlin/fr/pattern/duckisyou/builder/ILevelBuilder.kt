package fr.pattern.duckisyou.builder

import fr.pattern.duckisyou.*

interface ILevelBuilder {

    fun buildParameters(name: String, backgroundColor: String)
    fun buildMap(mapString: String)
    fun getLevel(): Level
}