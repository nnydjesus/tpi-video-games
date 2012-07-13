package ar.unq.tpi.nny.pacman.map

import com.uqbar.vainilla.DeltaState
import ar.unq.tpi.components.AnimationComponent
import ar.unq.tpi.nny.pacman.component.ReadImage
import ar.unq.tpi.nny.pacman.domain.Direction
import ar.unq.tpi.nny.pacman.domain.Ghost
import ar.unq.tpi.nny.pacman.domain.Position
import ar.unq.tpi.nny.pacman.util.GameConstants
import ar.unq.tpi.nny.pacman.util.GameEvents
import ar.unq.tpi.nny.pacman.scene.PlayScene
import ar.edu.unq.tpi.traits.Event

class GhostComponent(var ghost: Ghost, var name: String, x: Double, y: Double) extends AnimationComponent[PlayScene, GhostComponent](null, x, y) with ReadImage {

  protected def eventName = GameEvents.COLLIDE_PACMAN_WITH_STAR
  var remainingMove = 0D
  var velocity = 80
  var remainigPosition: Position = _
  var currentPosition: Position = _
  var prevDirection: Direction = _

  generateAnimations()
  this.setAppearance(northAnimation)
  var animationMap = Map(Direction.UP -> northAnimation, Direction.DOWN -> southAnimation, Direction.LEFT -> eastAnimation, Direction.RIGHT -> westAnimation)
  setZ(2)
  this.setX(ghost.currentPosition.row * GameConstants.TILE)
  this.setY(ghost.currentPosition.column * GameConstants.TILE)

  override def update(state: DeltaState) {
    super.update(state)
    var moving = false
    if (remainingMove == 0 || remainingMove == GameConstants.TILE) {
      this.setX(ghost.currentPosition.row * GameConstants.TILE)
      this.setY(ghost.currentPosition.column * GameConstants.TILE)

      remainingMove = GameConstants.TILE
      remainigPosition = ghost.currentPosition.clone()
      getScene().board.updateCreature(ghost)
      currentPosition = ghost.currentPosition.clone()
      currentPosition.removeToPosition(remainigPosition)
    }

    move(state)

    if (ghost.vulnerable) {
      this.setAppearance(vulnerableAnimation)
    } else {
      if (!ghost.currentDirection.equals(prevDirection)) {
        this.prevDirection = ghost.currentDirection
        this.setAppearance(animationMap(this.prevDirection))
      }
    }
    
    if(ghost.dead){
      this.setAppearance(eyeAnimation)
    }

    if (!ghost.dead  && this.ghost.currentPosition.equals(getScene().pacman.pacmam.currentPosition)) {
      dispatchEvent(new Event(GameEvents.COLLIDE_PACMAN_WITH_GHOST, this))
    }

  }

  def move(state: DeltaState) {
    val moveX = cantToMove(currentPosition.row * velocity * state.getDelta())
    val moveY = cantToMove(currentPosition.column * velocity * state.getDelta())
    this.move(moveX, moveY)
  }

  def cantToMove(dx: Double): Double = {
    var toMove = dx
    var totalMove = Math.abs(dx)

    if (remainingMove > totalMove) {
      remainingMove -= totalMove
    } else {
      if (toMove > 0) {
        toMove = remainingMove
      } else {
        toMove = -remainingMove
      }
      remainingMove = 0
    }
    return toMove
  }

}