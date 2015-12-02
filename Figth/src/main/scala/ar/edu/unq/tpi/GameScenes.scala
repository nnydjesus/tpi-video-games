package ar.edu.unq.tpi

import scala.collection.mutable.DoubleLinkedList
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.unq.tpi.components.SelectScene
import ar.unq.tpi.components.SpriteComponent
import java.awt.Color
import ar.unq.tpi.components.SpriteCenterComponent

class LoadingScene(characterAppearance1: CharacterAppearance, characterAppearance2: CharacterAppearance) extends GameScene() with TraitResources with EventGameComponent[LoadingScene] {

  override def setGame(game: Game) {
    super.setGame(game)
    this.addComponent(new GameComponent(new Rectangle(Color.BLACK, getGame().getDisplayWidth(), getGame().getDisplayHeight()), 0, 0))
    this.addComponent(new SpriteCenterComponent(GameImage.LOADING_B, getGame().getDisplayWidth(), getGame().getDisplayHeight()))
    this.addComponent(new SpriteComponent(characterAppearance1.vsImage, 180, 200))
    this.addComponent(new SpriteComponent(characterAppearance2.vsImage, 900, 200))
    this.addComponent(new SpriteCenterComponent(GameImage.LOADING, getGame().getDisplayWidth(), getGame().getDisplayHeight()))
    load()
  }

  def load() {
    new Thread() {
      override def run() {
        characterAppearance1.configureAppearance();
        characterAppearance2.configureAppearance();
        dispatchEvent(new Event(GameEvents.LOAD_RESOURCE, LoadingScene.this))
      }
    }.start()
  }

}

class SelectArenaScene(game: Fight, character1: CharacterAppearance, character2: CharacterAppearance) extends SelectScene() {

  var actuallyStage : Sprite = null
    		  
  //  
  //  this.addComponent(new SelectComponent(this, Arena1, initialX, initialY, width, height))
  //  this.addComponent(new SelectComponent(this, Arena2, initialX + (width) + padding, initialY, width, height))
  //  this.addComponent(new SelectComponent(this, Arena3, initialX + (2 * width) + (2 * padding), initialY, width, height))
  //  this.addComponent(new SelectComponent(this, Arena4, initialX + (3 * width) + (3 * padding), initialY, width, height))
  //
  //  var bigArenaComponent = new GameComponent[GameScene, Appearance](scaleBigArena(arenaSelected.image), 400, 300)
  //  this.addComponent(new GameComponent[GameScene, Animation](character1.selectedAnimation, 10, 300))
  //
  //  this.addComponent(bigArenaComponent)
  //  var start = GameImage.BUTTON_START
  //
  //  this.addComponent(new ButtonComponent(start, start, game.getDisplayWidth() - (start.getWidth() + 100), game.getDisplayHeight() - (start.getHeight() + 100), onStart))
  //  this.addComponent(new MouseComponent(GameImage.SWORD, 0, 0))
  //
  //  override def selectItem(arena: Selectable) {
  //    arenaSelected = arena
  //    bigArenaComponent.setAppearance(scaleBigArena(arena.image))
  //
  //  }
  //
  //  def onStart(delta: DeltaState) {
  //    game.playGame(character1, character2, arenaSelected)
  //  }
  //
  //  def scaleBigArena(sprite: Sprite) = sprite.scale(sizeBigArena.width / sprite.getWidth(), sizeBigArena.height / sprite.getHeight())
}
