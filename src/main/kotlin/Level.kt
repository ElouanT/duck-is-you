class Level(name: String, backgroundColor: String, map: Map) {
    var name: String = name
    var backgroundColor: String = backgroundColor
    var map : Map = map

    fun reset() {
        map.reset()
    }
}