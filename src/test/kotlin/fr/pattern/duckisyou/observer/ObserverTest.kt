package fr.pattern.duckisyou.observer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

import fr.pattern.duckisyou.enums.*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ObserverTest {
    lateinit var concretSubject: ConcretSubject

    @BeforeAll
    fun initialize() {
        concretSubject = ConcretSubject()
    }

    @Test
    fun observers() {
        var duck = Sprite(ESprite.DUCK, "sprite_duck.png")
        var flag = Sprite(ESprite.FLAG, "sprite_flag.png")
        var rock = Sprite(ESprite.ROCK, "sprite_rock.png")

        concretSubject.addObserver(duck)
        concretSubject.addObserver(flag)
        concretSubject.addObserver(rock)

        assertEquals(3, concretSubject.observers.size)

        concretSubject.removeObserver(rock)

        assertEquals(2, concretSubject.observers.size)

        concretSubject.removeObserver(duck)
        concretSubject.removeObserver(flag)

        assertEquals(0, concretSubject.observers.size)
    }

    @Test
    fun behaviors() {
        var duck = Sprite(ESprite.DUCK, "sprite_duck.png")
        var flag = Sprite(ESprite.FLAG, "sprite_flag.png")

        concretSubject.addObserver(duck)
        concretSubject.addObserver(flag)

        concretSubject.behavior = EBehavior.WIN
        concretSubject.notifyObservers()

        concretSubject.removeObserver(flag)

        concretSubject.behavior = EBehavior.MOVE
        concretSubject.notifyObservers()

        assertEquals(EBehavior.WIN, flag.behavior)
        assertEquals(EBehavior.MOVE, duck.behavior)
    }
}