package fr.pattern.duckisyou.factory

import fr.pattern.duckisyou.enums.ESprite
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BlockFactoryTest {
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
 }


}