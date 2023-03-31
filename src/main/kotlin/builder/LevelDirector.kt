package builder

import Level
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
            "special" -> levelBuilder = SpecialLevelBuilder()
            else -> levelBuilder = BaseLevelBuilder()
        }

        var mapString = fileString.subList(3, fileString.size)

        levelBuilder.buildParameters(name, color)
        levelBuilder.buildMap(mapString.joinToString(""))
        return levelBuilder.getLevel()
    }
}