class Level(n: Int, backgroundColor: String, map: Map) {
    var n : Int = n
    var backgroundColor: String = backgroundColor
    var map : Map = map

    fun reset() {
        map.reset()
    }
}