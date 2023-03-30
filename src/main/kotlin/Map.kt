import enums.EBehavior
import enums.EBlock
import enums.EDirection
import enums.ESprite
import observer.ConcretSubject
import observer.Sprite

class Map(
    var width: Int = 0,
    var height: Int = 0,
    var scaleFactor: Double = 0.0, // Redimension des sprites
) {
    var defaultCases: Array<GameObject?> = Array(width * height) { null } // Emplacement initial des cases utilisé par le reset
    var cases: Array<GameObject?> = Array(width * height) { null }
    var subjects: HashMap<ESprite, ConcretSubject> = HashMap() // Sujets des observers mettant à jour les comportements
    var blocks: ArrayList<Block> = ArrayList() // Liste des blocks pour vérifier les mises à jour de comportements

    init {
        // Initialisation des observers pour chaque type de sprites
        for (spriteType in ESprite.values()) {
            subjects[spriteType] = ConcretSubject()
        }
    }

    fun setGameObject(obj: GameObject, x: Int, y: Int) {
        // Ajout aux listes pour futur traitement
        if (obj is Sprite) {
            subjects.get(obj.type)!!.addObserver(obj) // Abonnement des sprites au listener associé à leur type
        } else if (obj is Block) {
            blocks.add(obj)
        }

        // Emplacement sur la map
        if (x<=width && y<=height) {
            defaultCases[y*width + x] = obj
            cases[y*width + x] = obj
        }
    }

    fun move(obj: GameObject, direction: EDirection) : Boolean {
        var i = cases.indexOf(obj)

        // Code dépendant de la direction
        var adjacentCaseIndex: Int

        when (direction) {
            EDirection.UP -> {
                if (i < width) return false // Si l'objet est sur la première ligne
                adjacentCaseIndex = i-width
            }
            EDirection.DOWN -> {
                if (i >= width*(height-1)) return false // Si l'objet est sur la dernière ligne
                adjacentCaseIndex = i+width
            }
            EDirection.LEFT -> {
                if (i%width == 0) return false // Si l'objet est sur la première colonne
                adjacentCaseIndex = i-1
            }
            EDirection.RIGHT -> {
                if (i%width == width-1) return false // Si l'objet est sur la dernière colonne
                adjacentCaseIndex = i+1
            }
        }

        // Code commun
        if (cases[adjacentCaseIndex] != null) { // Si la case de destination est occupée
            var adjacentCase : GameObject = cases[adjacentCaseIndex]!!
            when (adjacentCase.behavior) {
                EBehavior.MOVE, EBehavior.PUSH -> {
                    if (!move(adjacentCase, direction)) { // On pousse la case adjacente
                        return false // Si ce n'est pas possible pas de déplacement
                    }
                }
                EBehavior.DEFEAT -> {
                    if(obj!!.behavior == EBehavior.MOVE) reset()
                    return false
                }
                EBehavior.WIN -> {
                    if(obj!!.behavior == EBehavior.MOVE) nextLevel()
                    return false
                }
                EBehavior.STOP -> return false
            }
        }

        // Déplacement
        cases[adjacentCaseIndex] = obj
        cases[i] = null

        // Affichage
        when (direction) {
            EDirection.UP -> {
                obj.gameEntity.translateY(-48.0*scaleFactor)
            }
            EDirection.DOWN -> {
                obj.gameEntity.translateY(48.0*scaleFactor)
            }
            EDirection.LEFT -> {
                obj.gameEntity.translateX(-48.0*scaleFactor)
            }
            EDirection.RIGHT -> {
                obj.gameEntity.translateX(48.0*scaleFactor)
            }
        }
        return true
    }

    fun getAdjacentGameObject(gameObject: GameObject): ArrayList<GameObject> {
        var i = cases.indexOf(gameObject)
        var adjacentCases = ArrayList<GameObject>()

        if (i >= width && cases[i-width] != null) adjacentCases.add(cases[i-width]!!) // Haut
        if (i < width*(height-1) && cases[i+width] != null) adjacentCases.add(cases[i+width]!!) // Bas
        if (i%width != 0 && cases[i-1] != null) adjacentCases.add(cases[i-1]!!)  // Gauche
        if (i%width != width-1 && cases[i+1] != null) adjacentCases.add(cases[i+1]!!) // Droite

        return adjacentCases
    }

    fun checkForBehaviorChange() {
        var activeBlocks: ArrayList<Block> = ArrayList()

        for (blockSubject in blocks.filterIsInstance<BlockSubject>()) {
            var subject = subjects[blockSubject.type]!!
            subject.behavior = EBehavior.STOP

            for (blockIs in getAdjacentGameObject(blockSubject).filterIsInstance<BlockIs>()) {
                for (blockAction in getAdjacentGameObject(blockIs).filterIsInstance<BlockAction>()) {
                    // Enregistrement du triplet de blocks actifs
                    activeBlocks.add(blockSubject)
                    activeBlocks.add(blockIs)
                    activeBlocks.add(blockAction)

                    // Mise à jour du comportement des sprites
                    subject.behavior = blockAction.type
                }
            }

            subject.notifyObservers()
        }

        // Opacité des blocks actifs/inactifs
        for (block in blocks) {
            if (activeBlocks.contains(block)) block.gameEntity.opacity = 1.0
            else block.gameEntity.opacity = 0.5
        }
    }

    fun reset() {
        cases = defaultCases.copyOf()
        var i=0
        for (obj in cases) {
            var y = i/mapWidth
            var x = i%mapWidth
            if (obj != null) {
                obj.gameEntity.setPosition(x*48.0*scaleFactor, y*48.0*scaleFactor)
            }
            i++
        }
        checkForBehaviorChange()
    }
}