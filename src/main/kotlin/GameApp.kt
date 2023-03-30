import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameApplication.launch
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.onKeyDown
import com.almasb.fxgl.dsl.getGameScene
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import builder.*
import enums.EBehavior
import enums.EDirection

const val mapWidth : Int = 14
const val mapHeight : Int = 10
var scaleFactor : Double = 1.0
var levels : ArrayList<IBuilderLevel> = ArrayList()
lateinit var currentLevel : Level
var levelIndex = 0

class GameApp : GameApplication() {

    override fun initSettings(settings: GameSettings) {
        with(settings){
            width = mapWidth*(48.0*scaleFactor).toInt()
            height = mapHeight*(48.0*scaleFactor).toInt()
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
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.move(obj!!, EDirection.UP)
            }
            currentLevel.map.checkForBehaviorChange()
        }
        onKeyDown(KeyCode.DOWN) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.move(obj!!, EDirection.DOWN)
            }
            currentLevel.map.checkForBehaviorChange()
        }
        onKeyDown(KeyCode.LEFT) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE}
            for (obj in movableObjects) {
                currentLevel.map.move(obj!!, EDirection.LEFT)
            }
            currentLevel.map.checkForBehaviorChange()
        }
        onKeyDown(KeyCode.RIGHT) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.move(obj!!, EDirection.RIGHT)
            }
            currentLevel.map.checkForBehaviorChange()
        }
        onKeyDown(KeyCode.R) {
            currentLevel.reset()
        }
    }
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

    // Placement des nouveaux GameObject sur la map
    for (obj in level.map.cases) {
        var y = i/mapWidth
        var x = i%mapWidth
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
    if (levelIndex < levels.size) levelIndex++

    loadLevel(levels.get(levelIndex).getLevel())
}

fun  main(args: Array<String>) {
    // Initialisation
    // Ajout des niveaux
    levels.add(BuilderDemoLevel())
    levels.add(BuilderFinalLevel())
    // Construction du niveau
    currentLevel = levels.get(levelIndex).getLevel()

    // Lancement du jeu
    launch(GameApp::class.java,args)
}

