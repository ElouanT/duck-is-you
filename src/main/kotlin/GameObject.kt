import com.almasb.fxgl.entity.Entity
import enums.EBehavior

abstract class GameObject(img: String, behavior: EBehavior) {
    var image : String = img
    lateinit var gameEntity: Entity // Entité FXGL pour afficher l'image à l'écran
    open var behavior: EBehavior = behavior
}