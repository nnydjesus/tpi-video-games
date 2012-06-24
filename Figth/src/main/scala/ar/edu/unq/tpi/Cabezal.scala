package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameComponent
import ar.unq.tpi.components.SelectScene
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import com.uqbar.vainilla.appearances.Animation
import ar.unq.tpi.components.AnimationComponent

class Cabezal(animation: Animation) extends AnimationComponent[SelectCharacterScene,Cabezal](animation, 0, 0) with EventGameComponent[Cabezal] {

  val MOVE_TO_UP = new FunctionSceneListener((d) => this.moveUp())
  val MOVE_TO_DOWN = new FunctionSceneListener((d) => this.moveDown())
  val MOVE_TO_LEFT = new FunctionSceneListener((d) => this.moveLeft())
  val MOVE_TO_RIGHT = new FunctionSceneListener((d) => this.moveRight())
  val ACCEPT_SELECT_CHARACTER = new FunctionSceneListener((d) => this.accept())
  val BACK_SELECT_CHARACTER = new FunctionSceneListener((d) => this.backSelectedCharacter())

  var player: Player = null
  var matrixBase: MatrixSelectedCharacter = null
  var positionx: Int = 0
  var positiony: Int = 0

  def this(player: Player, animation: Animation, posx: Int, posy: Int, initX: Double, initY: Double) {
    this(animation)

    positionx = posx
    positiony = posy
    this.player = player
    setX((positionx * GameValues.WIDTH_SELECTED_CHARACTER) + initX)
    setY((positiony * GameValues.HEIGHT_SELECTED_CHARACTER) + initY)
  }

  def moveUp() {
    if (!matrixBase.isOverflow(positionx, positiony - 1)) {
      setY(getY() - GameValues.HEIGHT_SELECTED_CHARACTER)
      positiony = positiony - 1
      fireOnMove()
    }
  }

  def moveDown() {
    if (!matrixBase.isOverflow(positionx, positiony + 1)) {
      setY(getY() + GameValues.HEIGHT_SELECTED_CHARACTER)
      positiony = positiony + 1
      fireOnMove()
    }
  }
  def moveLeft() {
    if (!matrixBase.isOverflow(positionx - 1, positiony)) {
      setX(getX() - GameValues.WIDTH_SELECTED_CHARACTER)
      positionx = positionx - 1
      fireOnMove()
    }
  }
  def moveRight() {
    if (!matrixBase.isOverflow(positionx + 1, positiony)) {
      setX(getX() + GameValues.WIDTH_SELECTED_CHARACTER)
      positionx = positionx + 1
      fireOnMove()
    }
  }
  def accept() {
    var selectCharacter: SelectableCharacter = matrixBase(positionx, positiony)
    dispatchEvent(new Event(GameEvents.CABEZAL_SELECTED, this, selectCharacter))
    removeListener()
    addListenerChangeCharacterSelected()
  }

  def fireOnMove() {
    dispatchEvent(new Event(GameEvents.CABEZAL_MOVIDO, this, matrixBase(positionx, positiony)))
  }

  def removeListener() {
    this.getScene().removeKeyPressetListener(this, player.UP, MOVE_TO_UP)
    this.getScene().removeKeyPressetListener(this, player.DOWN, MOVE_TO_DOWN)
    this.getScene().removeKeyPressetListener(this, player.LEFT, MOVE_TO_LEFT)
    this.getScene().removeKeyPressetListener(this, player.RIGHT, MOVE_TO_RIGHT)
    this.getScene().removeKeyPressetListener(this, player.ENTER, ACCEPT_SELECT_CHARACTER)
  }

  def configurationListener() {

    this.getScene().addKeyPressetListener(this, player.UP, MOVE_TO_UP)
    this.getScene().addKeyPressetListener(this, player.DOWN, MOVE_TO_DOWN)
    this.getScene().addKeyPressetListener(this, player.LEFT, MOVE_TO_LEFT)
    this.getScene().addKeyPressetListener(this, player.RIGHT, MOVE_TO_RIGHT)
    this.getScene().addKeyPressetListener(this, player.ENTER, ACCEPT_SELECT_CHARACTER)
  }
  
  def addListenerChangeCharacterSelected(){
    this.getScene().addKeyPressetListener(this,player.ENTER, BACK_SELECT_CHARACTER)
  }
  def removeListenerChangeCharacterSelected(){
    this.getScene().removeKeyPressetListener(this,player.ENTER, BACK_SELECT_CHARACTER)
  }
  
  def backSelectedCharacter(){
    dispatchEvent(new Event(GameEvents.BACK_SELECT_CHARACTER, this, null))
    this.removeListenerChangeCharacterSelected()
    this.configurationListener()
  }
  
  override def setScene(scene: SelectCharacterScene) {
    super.setScene(scene)
    this.matrixBase = scene.matrixCharacter
    this.configurationListener()
    fireOnMove()
  }
}