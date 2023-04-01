package fr.pattern.duckisyou.factory

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

import fr.pattern.duckisyou.enums.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class FactoryTest {
 lateinit var factory: BlockFactory

 @BeforeAll
 fun initialize() {
  factory = BlockFactory()
 }

 @Test
 fun blockIs() {
  assertEquals("block_is.png", factory.createBlock().image)
 }

 @Test
 fun blockSubject() {
  assertEquals("block_duck.png", factory.createBlock(ESprite.DUCK).image)
  assertEquals("block_flag.png", factory.createBlock(ESprite.FLAG).image)
  assertEquals("block_wall.png", factory.createBlock(ESprite.WALL).image)
  assertEquals("block_rock.png", factory.createBlock(ESprite.ROCK).image)
  assertEquals("block_lava.png", factory.createBlock(ESprite.LAVA).image)
 }

 @Test
 fun blockAction() {
  assertEquals("block_you.png", factory.createBlock(EBehavior.MOVE).image)
  assertEquals("block_stop.png", factory.createBlock(EBehavior.STOP).image)
  assertEquals("block_push.png", factory.createBlock(EBehavior.PUSH).image)
  assertEquals("block_defeat.png", factory.createBlock(EBehavior.DEFEAT).image)
  assertEquals("block_win.png", factory.createBlock(EBehavior.WIN).image)
 }
}