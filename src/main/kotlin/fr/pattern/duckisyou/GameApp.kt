package fr.pattern.duckisyou

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameApplication.launch
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.onKeyDown
import com.almasb.fxgl.dsl.getGameScene
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import java.nio.file.Files
import java.nio.file.Paths
import fr.pattern.duckisyou.builder.LevelDirector
import fr.pattern.duckisyou.enums.*

const val mapWidth : Int = 15
const val mapHeight : Int = 10
var scaleFactor : Double = 1.0
var levels : ArrayList<String> = ArrayList()
lateinit var currentLevel : Level
var levelIndex = 0

class GameApp : GameApplication() {

    override fun initSettings(settings: GameSettings) {
        with(settings){
            width = mapWidth *(48.0* scaleFactor).toInt()
            height = mapHeight *(48.0* scaleFactor).toInt()
            title ="Duck is you"
            version = "1.0.0"
            appIcon = "sprite_duck.png"
        }
    }

    override fun initGame() {
        loadLevel(currentLevel)
    }

    override fun initInput() {
        onKeyDown(KeyCode.UP) {
            move(EDirection.UP)
        }
        onKeyDown(KeyCode.DOWN) {
            move(EDirection.DOWN)
        }
        onKeyDown(KeyCode.LEFT) {
            move(EDirection.LEFT)
        }
        onKeyDown(KeyCode.RIGHT) {
            move(EDirection.RIGHT)
        }
        onKeyDown(KeyCode.R) {
            currentLevel.reset()
        }
    }
}

fun move(direction: EDirection) {
    var movableObjects: List<GameObject?>

    // Tri des sprites de gauche à droite ou de droite à gauche pour gérer les déplacements de plusieurs sprites simultanément sans bloquage
    when (direction) {
        EDirection.UP -> movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
        EDirection.DOWN -> movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }.reversed()
        EDirection.LEFT -> movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
        EDirection.RIGHT -> movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }.reversed()
    }

    for (obj in movableObjects) {
        currentLevel.map.move(obj!!, direction)
    }
    currentLevel.map.checkForBehaviorChange()
}

fun unloadCurrentLevel() {
    var map = currentLevel.map
    // Retrait de toutes les entités du niveau actuel
    for (obj in map.cases) {
        if (obj != null) {
            obj.gameEntity.removeFromWorld()
        }
    }
}

fun loadLevel(level: Level) {
    // Changement d'arrière plan
    getGameScene().setBackgroundColor(Color.web(level.backgroundColor))
    var i = 0

    // Placement des nouveaux fr.pattern.duckisyou.GameObject sur la map
    for (obj in level.map.cases) {
        var y = i/ mapWidth
        var x = i% mapWidth
        if (obj != null) {
            obj.gameEntity = FXGL.entityBuilder()
                .at(x * 48.0 * scaleFactor, y * 48.0 * scaleFactor)
                .view(obj.image)
                .scale(scaleFactor, scaleFactor)
                .buildAndAttach();
        }
        i++
    }

    // Reactualise
    currentLevel = level
    currentLevel.reset()
}

fun nextLevel() {
    unloadCurrentLevel()

    // Passage au niveau suivant
    if (levelIndex < levels.size-1) levelIndex++

    loadLevel(LevelDirector(levels.get(levelIndex)).make())
}

fun getMapFilesPath(): ArrayList<String>
{
    var paths = ArrayList<String>()
    val projectAbsolutePath = Paths.get("").toAbsolutePath().toString()
    val resourcesPath = Paths.get(projectAbsolutePath, "/src/main/resources")
    Files.walk(resourcesPath)
        .filter { path -> Files.isRegularFile(path) }
        .filter { path -> path.toString().endsWith(".duck") }
        .forEach { path -> paths.add(path.toString()) }
    paths.sort()
    return paths
}

fun  main(args: Array<String>) {
    // Initialisation

    // Ajout des niveaux
    levels = getMapFilesPath()

    // Construction du niveau
    currentLevel = LevelDirector(levels.get(levelIndex)).make()

    // Lancement du jeu
    launch(GameApp::class.java,args)
}
