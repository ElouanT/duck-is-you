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
    var defaultCases: Array<GameObject?> = Array(width * height) { null }, // Emplacement initial des cases utilisé par le reset
    var cases: Array<GameObject?> = Array(width * height) { null },
    var blockIs: ArrayList<GameObject> = ArrayList(), // Liste des blocks IS pour vérifier les mises à jour de comportement
    var subjects: HashMap<ESprite, ConcretSubject> = HashMap() // Sujets des observers
) {
    init {
        // Initialisation des observers pour chaque type de sprites
        for (spriteType in ESprite.values()) {
            subjects[spriteType] = ConcretSubject()
        }
    }

    fun setGameObject(obj: GameObject, x: Int, y: Int) {
        // Ajout aux listes pour futur traitement
        if (obj is Sprite) {
            subjects.get(obj.type)!!.addObserver(obj)
        } else if (obj is Block && obj.type == EBlock.IS) {
            blockIs.add(obj)
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

    fun checkForBehaviorChange() { // /!\ Ne prends pas en compte de multiples block pour un même "IS" /!\
        for (b in blockIs) {
            var i = cases.indexOf(b)

            var blockSubject: Block? = null
            var blockAction: Block? = null

            if (i%width != 0) { // !première colonne
                var leftCase = cases[i-1]
                if (leftCase != null && leftCase is Block) {
                    if (leftCase.type == EBlock.SUBJECT) blockSubject = leftCase
                    if (leftCase.type == EBlock.ACTION) blockAction = leftCase
                }
            }
            if (i%width != width-1) { // !dernière colonne
                var rightCase = cases[i+1]
                if (rightCase != null && rightCase is Block) {
                    if (rightCase.type == EBlock.SUBJECT) blockSubject = rightCase
                    if (rightCase.type == EBlock.ACTION) blockAction = rightCase
                }
            }
            if (i >= width) { // !première ligne
                var upCase = cases[i-width]
                if (upCase != null && upCase is Block) {
                    if (upCase.type == EBlock.SUBJECT) blockSubject = upCase
                    if (upCase.type == EBlock.ACTION) blockAction = upCase
                }
            }
            if (i < width*(height-1)) { // !dernière ligne
                var downCase = cases[i+width]
                if (downCase != null && downCase is Block) {
                    if (downCase.type == EBlock.SUBJECT) blockSubject = downCase
                    if (downCase.type == EBlock.ACTION) blockAction = downCase
                }
            }
            
            if (blockSubject != null) {
                var subject = subjects[blockSubject.spriteType!!]!!
                if (blockAction != null) {
                    subject.behavior = blockAction.behaviorType!!
                } else {
                    subject.behavior = EBehavior.STOP
                }
                subject.notifyObservers()
            }
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