package fr.pattern.duckisyou.builder

import fr.pattern.duckisyou.*
import java.io.File

class LevelDirector(val filePath: String) {

    fun make(): Level {
        var fileString : List<String> = File(filePath).readLines()

        var name = fileString[0]
        var color = fileString[2]

        var levelBuilder: ILevelBuilder
        when (fileString[1]) {
            "base" -> levelBuilder = BaseLevelBuilder()
            "aquatic" -> levelBuilder = AquaticLevelBuilder()
            "volcano" -> levelBuilder = VolcanoLevelBuilder()
            "special" -> levelBuilder = SpecialLevelBuilder()
            else -> levelBuilder = BaseLevelBuilder()
        }

        var mapString = fileString.subList(3, fileString.size)

        levelBuilder.buildParameters(name, color)
        levelBuilder.buildMap(mapString.joinToString(""))
        return levelBuilder.getLevel()
    }
}