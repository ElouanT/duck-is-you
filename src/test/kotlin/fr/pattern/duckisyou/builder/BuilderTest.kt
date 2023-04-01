package fr.pattern.duckisyou.builder

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

import java.nio.file.Files
import java.nio.file.Paths

import fr.pattern.duckisyou.enums.*
import fr.pattern.duckisyou.factory.*
import fr.pattern.duckisyou.observer.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BuilderTest {
    lateinit var paths: ArrayList<String>

    @BeforeAll
    fun initialize() {
        paths = ArrayList<String>()
        val projectAbsolutePath = Paths.get("").toAbsolutePath().toString()
        val resourcesPath = Paths.get(projectAbsolutePath, "/src/main/resources")
        Files.walk(resourcesPath)
            .filter { path -> Files.isRegularFile(path) }
            .filter { path -> path.toString().endsWith(".duck") }
            .forEach { path -> paths.add(path.toString()) }
        paths.sort()
    }

    @Test
    fun mapGeneration() {
        var level = LevelDirector(paths[1]).make()

        assertEquals(ESprite.DUCK, (level.map.cases[1] as BlockSubject).type)
        assertTrue(level.map.cases[2] is BlockIs)
        assertEquals(EBehavior.MOVE, (level.map.cases[3] as BlockAction).type)
    }

    @Test
    fun levelTypes() {
        var baseLevel = LevelDirector(paths[1]).make()
        var volcanoLevel = LevelDirector(paths[4]).make()
        var aquaticLevel = LevelDirector(paths[7]).make()

        assertEquals("sprite_wall_base.png", (baseLevel.map.cases[7] as Sprite).image)
        assertEquals("sprite_lava.png", (volcanoLevel.map.cases[0] as Sprite).image)
        assertEquals("sprite_duck_aquatic.png", (aquaticLevel.map.cases[78] as Sprite).image)
    }
}