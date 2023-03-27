class Map(width: Int, height: Int, scaleFactor: Double) {
    var width = width
    var height = height
    var scaleFactor = scaleFactor
    var defaultCases : Array<GameObject?> = Array(width*height){null} // Emplacement par défaut utilisé pour le reset
    var cases : Array<GameObject?> = Array(width*height){null}

    fun setGameObject(obj: GameObject, x: Int, y: Int) {
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
                Behavior.MOVE, Behavior.PUSH -> {
                    if (!moveLeft(cases[i-1]!!)) { // On pousse la case de gauche
                        return false // Sinon
                    }
                }
                Behavior.STOP -> {
                    return false
                }
            }
        }
        // Déplacement
        cases[i-1] = obj
        cases[i] = null
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
                Behavior.MOVE, Behavior.PUSH -> {
                    if (!moveRight(cases[i + 1]!!)) { // On pousse la case de droite
                        return false // Sinon
                    }
                }
                Behavior.STOP -> {
                    return false
                }
            }
        }
        // Déplacement
        cases[i+1] = obj
        cases[i] = null
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
                Behavior.MOVE, Behavior.PUSH -> {
                    if (!moveUp(cases[i - width]!!)) { // On pousse la case du dessus
                        return false // Sinon
                    }
                }
                Behavior.STOP -> {
                    return false
                }
            }
        }
        // Déplacement
        cases[i-width] = obj
        cases[i] = null
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
                Behavior.MOVE, Behavior.PUSH -> {
                    if (!moveDown(cases[i + width]!!)) { // On pousse la case du dessous
                        return false // Sinon
                    }
                }
                Behavior.STOP -> {
                    return false
                }
            }
        }
        // Déplacement
        cases[i+width] = obj
        cases[i] = null
        // Affichage
        obj.gameEntity.translateY(48.0*scaleFactor)
        return true
    }

    fun checkForBehaviorChange() {

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