package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameComponent
import ar.unq.tpi.components.SelectScene
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import ar.edu.unq.tpi.traits.FunctionSceneListener

class Cabezal extends GameComponent[SelectCharacterScene, Sprite] {

  var player: Player = null
  var matrixBase: MatrixSelectedCharacter = null
  var pox : Int  = 0
  var poy : Int  = 0
  
  def this(playe: Player, border: Sprite, x: Int, y: Int) {
    this()
    
    pox = x
    poy = y
    player = playe
    matrixBase = getScene().matrixCharacter
    setAppearance(border)
    setX(x * GameValues.WIDTH_SELECTED_CHARACTER + GameValues.COORD_X_MATRIX)
    setY(y * GameValues.HEIGHT_SELECTED_CHARACTER + GameValues.COORD_Y_MATRIX)
  }

  def moveUp() {
    if (!matrixBase.isOverflow(pox,poy - 1)) {
      setY(getY() -GameValues.HEIGHT_SELECTED_CHARACTER)
      poy = poy -1
    }
  }

  def moveDown() {
    if (!matrixBase.isOverflow(pox,poy + 1)) {
      setY(getY() + GameValues.HEIGHT_SELECTED_CHARACTER)
      poy = poy +1
    }
  }
  def moveLeft() {
    if (!matrixBase.isOverflow(pox - 1, poy)) {
      setX(getX() - GameValues.WIDTH_SELECTED_CHARACTER)
    pox = pox -1
    }
  }
  def moveRight() {
    if (!matrixBase.isOverflow(pox + 1, poy)) {
      setX(getX() + GameValues.WIDTH_SELECTED_CHARACTER)
      pox = pox + 1
    }
  }
  def accept() {

  }

  def configurationListener() {

    this.getScene().addKeyPressetListener(this, player.ENTER, new FunctionSceneListener((d) => this.accept()))
    this.getScene().addKeyPressetListener(this, player.UP, new FunctionSceneListener((d) => this.moveUp()))
    this.getScene().addKeyPressetListener(this, player.DOWN, new FunctionSceneListener((d) => this.moveDown()))
    this.getScene().addKeyPressetListener(this, player.LEFT, new FunctionSceneListener((d) => this.moveLeft()))
    this.getScene().addKeyPressetListener(this, player.RIGHT, new FunctionSceneListener((d) => this.moveRight()))
  }

  override def setScene(scene: SelectCharacterScene) {
    super.setScene(scene)
    this.configurationListener()
  }
}