package ar.edu.unq.tpi
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.components.SpriteCenterComponent
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

class StageComponent(sprite: Sprite, width: Double, height: Double) extends SpriteCenterComponent[SelectCharacterScene](sprite, width, height) with EventGameComponent[StageComponent] {

  val CHANGE_STAGE_LEFT = new FunctionSceneListener((d) => this.changePreviousStage())
  val CHANGE_STAGE_RIGHT = new FunctionSceneListener((d) => this.changeNextStage())
  val ACCEPT_STAGE = new FunctionSceneListener((d) => this.acceptStage())

  var player: Player = null

  def this(player: Player, sprite: Sprite, width: Double, height: Double) {
    this(sprite, width, height)
    this.player = player
  }

  def configurationListener() {
    this.getScene().addKeyPressetListener(this, CHANGE_STAGE_LEFT, player.LEFT.key)
    this.getScene().addKeyPressetListener(this, CHANGE_STAGE_RIGHT, player.RIGHT.key)
    this.getScene().addKeyPressetListener(this, ACCEPT_STAGE, player.ENTER.key)
  }
  def removeListener() {
    this.getScene().removeKeyPressetListener(this, CHANGE_STAGE_LEFT, player.LEFT.key)
    this.getScene().removeKeyPressetListener(this, CHANGE_STAGE_RIGHT, player.RIGHT.key)
    this.getScene().removeKeyPressetListener(this, ACCEPT_STAGE, player.ENTER.key)
  }

  def changeNextStage() {
    dispatchEvent(new Event(GameEvents.CHANGE_NEXT_STAGE, this, null))
  }
  def changePreviousStage() {
    dispatchEvent(new Event(GameEvents.CHANGE_PREVIOUS_STAGE, this, null))
  }
  def acceptStage() {
    var stage = this.getAppearance()
    dispatchEvent(new Event(GameEvents.ACCEPT_STAGE, this, stage ))
    this.removeListener()
  }
}