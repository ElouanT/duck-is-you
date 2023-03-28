import enums.EBehavior
import enums.EBlock
import enums.ESprite

class Map() {
    var width = 0
    var height = 0
    var scaleFactor = 0.0
    lateinit var defaultCases : Array<GameObject?> // Emplacement par défaut utilisé pour le reset
    lateinit var cases : Array<GameObject?>

    var blockIs: ArrayList<GameObject> = ArrayList()  // IS Blocks

    var subjects: HashMap<ESprite, ConcretSubject> = HashMap() // Subjects for observers (one for each type of sprite)

    constructor(width: Int, height: Int, scaleFactor: Double) : this() {
        this.width = width
        this.height = height
        this.scaleFactor = scaleFactor
        defaultCases = Array(width*height){null}
        cases = Array(width*height){null}

        for (spriteType in ESprite.values()) {
            subjects.set(spriteType, ConcretSubject())
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
            cases[y*width + x] = obj
            defaultCases[y*width + x] = obj
        }
    }

    fun moveLeft(obj: GameObject) : Boolean {
        var i = cases.indexOf(obj)
        if (i%width == 0) { // Si l'obj se situe sur la première colonne
            return false
        }
        else if (cases[i-1] != null) { // Si la case de gauche est occupée
            when (cases[i-1]!!.behavior) {
                EBehavior.MOVE, EBehavior.PUSH -> {
                    if (!moveLeft(cases[i-1]!!)) { // On pousse la case de gauche
                        return false // Sinon
                    }
                }
                EBehavior.STOP -> {
                    return false
                }
                EBehavior.DEFEAT -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        reset()
                    }
                    return false
                }
                EBehavior.WIN -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        nextLevel()
                    }
                    return false
                }
            }
        }
        // Déplacement
        cases[i-1] = obj
        cases[i] = null
        if (obj.behavior == EBehavior.MOVE) checkForBehaviorChange()
        // Affichage
        obj.gameEntity.translateX(-48.0*scaleFactor)
        return true
    }

    fun moveRight(obj: GameObject) : Boolean {
        var i = cases.indexOf(obj)
        if (i%width == width-1) { // Si l'obj se situe sur la dernière colonne
            return false
        }
        else if (cases[i+1] != null) { // Si la case de droite est occupée
            when (cases[i+1]!!.behavior) {
                EBehavior.MOVE, EBehavior.PUSH -> {
                    if (!moveRight(cases[i + 1]!!)) { // On pousse la case de droite
                        return false // Sinon
                    }
                }
                EBehavior.STOP -> {
                    return false
                }
                EBehavior.DEFEAT -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        reset()
                    }
                    return false
                }
                EBehavior.WIN -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        nextLevel()
                    }
                    return false
                }
            }
        }
        // Déplacement
        cases[i+1] = obj
        cases[i] = null
        if (obj.behavior == EBehavior.MOVE) checkForBehaviorChange()
        // Affichage
        obj.gameEntity.translateX(48.0*scaleFactor)
        return true
    }

    fun moveUp(obj: GameObject) : Boolean {
        var i = cases.indexOf(obj)
        if (i < width) { // Si l'obj se situe sur la première ligne
            return false
        }
        else if (cases[i-width] != null) { // Si la case du dessus est occupée
            when (cases[i-width]!!.behavior) {
                EBehavior.MOVE, EBehavior.PUSH -> {
                    if (!moveUp(cases[i - width]!!)) { // On pousse la case du dessus
                        return false // Sinon
                    }
                }
                EBehavior.STOP -> {
                    return false
                }
                EBehavior.DEFEAT -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        reset()
                    }
                    return false
                }
                EBehavior.WIN -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        nextLevel()
                    }
                    return false
                }
            }
        }
        // Déplacement
        cases[i-width] = obj
        cases[i] = null
        if (obj.behavior == EBehavior.MOVE) checkForBehaviorChange()
        // Affichage
        obj.gameEntity.translateY(-48.0*scaleFactor)
        return true
    }

    fun moveDown(obj: GameObject) : Boolean {
        var i = cases.indexOf(obj)
        if (i >= width*(height-1)) { // Si l'obj se situe sur la dernière ligne
            return false
        }
        else if (cases[i+width] != null) { // Si la case du dessous est occupée
            when (cases[i+width]!!.behavior) {
                EBehavior.MOVE, EBehavior.PUSH -> {
                    if (!moveDown(cases[i + width]!!)) { // On pousse la case du dessous
                        return false // Sinon
                    }
                }
                EBehavior.STOP -> {
                    return false
                }
                EBehavior.DEFEAT -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        reset()
                    }
                    return false
                }
                EBehavior.WIN -> {
                    if(cases[i]!!.behavior == EBehavior.MOVE) {
                        nextLevel()
                    }
                    return false
                }
            }
        }
        // Déplacement
        cases[i+width] = obj
        cases[i] = null
        if (obj.behavior == EBehavior.MOVE) checkForBehaviorChange()
        // Affichage
        obj.gameEntity.translateY(48.0*scaleFactor)
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
    }
}