import com.almasb.fxgl.entity.Entity

abstract class GameObject(img: String, behavior: Behavior) {
    var image : String = img
    lateinit var gameEntity: Entity // Fxgl entity to display and move the image on screen
    var behavior: Behavior = behavior
}