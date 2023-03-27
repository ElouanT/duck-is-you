import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameApplication.launch
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.FXGL.Companion.onKeyDown
import com.almasb.fxgl.dsl.getGameScene
import com.almasb.fxgl.entity.Entity
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color

const val mapWidth : Int = 14
const val mapHeight : Int = 10
var scaleFactor : Double = 1.0
lateinit var levels : Array<Level>
lateinit var currentLevel : Level

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
        var map = currentLevel.map
        onKeyDown(KeyCode.RIGHT) {
            var movableObjects = map.cases.filter { obj -> obj != null && obj.behavior == Behavior.MOVE }
            for (obj in movableObjects) {
                map.moveRight(obj!!)
            }
        }
        onKeyDown(KeyCode.LEFT) {
            var movableObjects = map.cases.filter { obj -> obj != null && obj.behavior == Behavior.MOVE}
            for (obj in movableObjects) {
                map.moveLeft(obj!!)
            }
        }
        onKeyDown(KeyCode.UP) {
            var movableObjects = map.cases.filter { obj -> obj != null && obj.behavior == Behavior.MOVE }
            for (obj in movableObjects) {
                map.moveUp(obj!!)
            }
        }
        onKeyDown(KeyCode.DOWN) {
            var movableObjects = map.cases.filter { obj -> obj != null && obj.behavior == Behavior.MOVE }
            for (obj in movableObjects) {
                map.moveDown(obj!!)
            }
        }
        onKeyDown(KeyCode.R) {
            currentLevel.reset()
        }
    }
}

fun  main(args: Array<String>) {
    // initialize game
    var map = Map(mapWidth, mapHeight, scaleFactor)
    map.setGameObject(Sprite("sprite_duck.png", Behavior.MOVE), 0, 1)
    map.setGameObject(Sprite("sprite_wall.png", Behavior.STOP), 1, 0)
    map.setGameObject(Sprite("sprite_wall.png", Behavior.STOP), 1, 1)
    map.setGameObject(Sprite("sprite_wall.png", Behavior.STOP), 2, 1)
    map.setGameObject(Sprite("sprite_rock.png", Behavior.PUSH), 4, 4)
    currentLevel = Level(0, map)

    // lauch game
    launch(GameApp::class.java,args)
}

