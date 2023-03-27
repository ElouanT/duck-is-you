import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameApplication.launch
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.onKeyDown
import com.almasb.fxgl.dsl.getGameScene
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color

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
        getGameScene().setBackgroundColor(Color.rgb(20, 25, 30))
        var map = currentLevel.map
        var i = 0
        // Placement des GameObject sur la map
        for (obj in map.cases) {
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
    }

    override fun initInput() {
        onKeyDown(KeyCode.RIGHT) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.moveRight(obj!!)
            }
        }
        onKeyDown(KeyCode.LEFT) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE}
            for (obj in movableObjects) {
                currentLevel.map.moveLeft(obj!!)
            }
        }
        onKeyDown(KeyCode.UP) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.moveUp(obj!!)
            }
        }
        onKeyDown(KeyCode.DOWN) {
            var movableObjects = currentLevel.map.cases.filter { obj -> obj != null && obj.behavior == EBehavior.MOVE }
            for (obj in movableObjects) {
                currentLevel.map.moveDown(obj!!)
            }
        }
        onKeyDown(KeyCode.R) {
            reset()
        }
    }
}

fun reset(){
    currentLevel.reset()
}
fun nextLevel(){
    var map = currentLevel.map
    // Retrait de toutes les entités du niveau actuel
    for (obj in map.cases) {
        if (obj != null) {
            obj.gameEntity.removeFromWorld()
        }
    }
    var i = 0

    // Passage au niveau suivant
    levelIndex++
    var nextLevel = buildLevel(levels.get(levelIndex))

    // Placement des nouveaux GameObject sur la map
    for (obj in nextLevel.map.cases) {
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
    currentLevel = nextLevel
    currentLevel.reset()
}

fun buildLevel(levelBuilder: IBuilderLevel): Level {
    levelBuilder.buildDuck()
    levelBuilder.buildFlag()
    levelBuilder.buildSprites()
    levelBuilder.buildBlocks()
    return levelBuilder.getLevel()
}

fun  main(args: Array<String>) {
    // Initialisation
    // Ajout des niveaux
    levels.add(BuilderDemoLevel())
    levels.add(BuilderFinalLevel())
    // Construction du niveau
    currentLevel = buildLevel(levels.get(levelIndex))

    // Lancement du jeu
    launch(GameApp::class.java,args)
}

