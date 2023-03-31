package builder

import Level

interface ILevelBuilder {

    fun buildParameters(name: String, backgroundColor: String)
    fun buildMap(mapString: String)
    fun getLevel(): Level
}