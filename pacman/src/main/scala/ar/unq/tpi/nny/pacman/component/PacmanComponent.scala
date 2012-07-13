package ar.unq.tpi.nny.pacman.component
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.unq.tpi.collide.Colicionable
import ar.unq.tpi.collide.PixelPerfectCollition
import ar.unq.tpi.components.AnimationComponent
import ar.unq.tpi.components.SpriteComponent
import ar.unq.tpi.nny.pacman.domain.Direction
import ar.unq.tpi.nny.pacman.domain.Pacman
import ar.unq.tpi.nny.pacman.domain.Position
import ar.unq.tpi.nny.pacman.map.GhostComponent
import ar.unq.tpi.nny.pacman.scene.PlayScene
import ar.unq.tpi.nny.pacman.util.GameConstants
import ar.unq.tpi.nny.pacman.util.GameImages

class PacmanComponent(animation: Animation, x: Double, y: Double) extends AnimationComponent[PlayScene, PacmanComponent](animation, x, y) with ReadImage {

  val velocity = 80
  var movingState: MovingState = _
  var pendingMovingState: MovingState = _
  var remainingMove = 0D
  var pacmam = new Pacman(new Position(x.toInt / GameConstants.TILE, y.toInt / GameConstants.TILE), Direction.LEFT)
  val dieSound = getSound("Die.wav")
  val eatGhost = getSound("Eat Ghost.wav")

  lazy val deathAnimation = new Animation(0.2, spriteS(sizeSprite, sizeSprite, 5, 2, GameImages.DEATH_PACMAN.getImage(), scale, scale))

  def this(x: Double, y: Double) {
    this(null, x, y)
    generateAnimations();
    this.setAppearance(eastAnimation)
    setZ(10)
  }

  def name = "pacman.png"

  override def setScene(scene: PlayScene) {
    super.setScene(scene)
    configureListeners()
  }

  def configureListeners() {
    getScene().addKeyPressetListener(this, new FunctionSceneListener(s => this.changeMovingState(UpMoving)), Key.UP)
    getScene().addKeyPressetListener(this, new FunctionSceneListener(s => this.changeMovingState(DownMoving)), Key.DOWN)
    getScene().addKeyPressetListener(this, new FunctionSceneListener(s => this.changeMovingState(LeftMoving)), Key.LEFT)
    getScene().addKeyPressetListener(this, new FunctionSceneListener(s => this.changeMovingState(RightMoving)), Key.RIGHT)
  }

  override def update(state: DeltaState) {
    super.update(state)
    pacmam.updateTimer(state.getDelta())
    if (this.pacmam.dead) {
      this.setAppearance(deathAnimation)
      if (deathAnimation.finish()) {
        deathAnimation.reset()
        this.getScene().board.restart()
        this.setX(pacmam.startPosition.row*GameConstants.TILE)
        this.setY(pacmam.startPosition.column*GameConstants.TILE)
      }
      pendingMovingState  = null
      movingState = LeftMoving
    } else {
      var moving = false
      if (remainingMove == 0D && pendingMovingState != null) {
        remainingMove = GameConstants.TILE
        if (this.pendingMovingState.movingOn(this, state)) {
          movingState = pendingMovingState
          pendingMovingState = null
          moving = true
        }
      }

      if (!moving && movingState != null) {
        if (remainingMove <= 0) {
          remainingMove = GameConstants.TILE
        }
        this.movingState.movingOn(this, state)
      }
    }
  }

  def eat(ghost: GhostComponent) {
    if(!pacmam.dead){
	    pacmam.updateStatus(ghost.ghost)
	    if(pacmam.dead){
	      dieSound.play()
	    }else{
	      eatGhost.play()
	    }
    }
  }

  def changeMovingState(state: MovingState) {
    this.pendingMovingState = state
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

  def move(collide: (PixelPerfectCollition[_, _]) => (Colicionable[_, _]) => Boolean, action: () => Tuple2[Double, Double], direction: Direction): Boolean = {
    val nextMove = action()
    pacmam.currentDirection = direction
    var position: Position = null
    var next = false
    if (((remainingMove + Math.abs(nextMove._1) + Math.abs(nextMove._2)) % GameConstants.TILE) == 0) {
      position = pacmam.getNextPosition()
      next = true
    } else {
      position = pacmam.currentPosition
    }

    if (getScene().board.isPositionValid(position) && getScene().board.isBlockValid(position)) {
      this.move(nextMove._1, nextMove._2)
      if (next) {
        getScene().board.updateCreature(pacmam)
      }
      return true
    } else {
      remainingMove = 0
      return false
    }
  }

  def moveUp(state: DeltaState): Boolean = {
    var canMove = move(w => w.impactTop, () => (0, cantToMove((-GameConstants.TILE - velocity) * state.getDelta())), Direction.UP)
    if (canMove) {
      this.setAppearance(northAnimation)
    }
    return canMove
  }

  def moveDown(state: DeltaState): Boolean = {
    var canMove = move(w => w.impactBotton, () => (0, cantToMove((GameConstants.TILE + velocity) * state.getDelta())), Direction.DOWN)
    if (canMove) {
      this.setAppearance(southAnimation)
    }
    return canMove
  }

  def moveLeft(state: DeltaState): Boolean = {
    var canMove = move(w => w.impactLeft, () => (cantToMove((-GameConstants.TILE - velocity) * state.getDelta()), 0), Direction.LEFT)
    if (canMove) {
      this.setAppearance(eastAnimation)
    }
    return canMove
  }

  def moveRight(state: DeltaState): Boolean = {
    var canMove = move(w => w.impactRight, () => (cantToMove((GameConstants.TILE + velocity) * state.getDelta()), 0), Direction.RIGHT)
    if (canMove) {
      this.setAppearance(westAnimation)
    }
    return canMove
  }

  def restore(x: Double, y: Double) {
    setX(x)
    setY(y)
  }
}

sealed abstract class MovingState {
  def movingOn(pacman: PacmanComponent, state: DeltaState): Boolean
}

case object LeftMoving extends MovingState {
  def movingOn(pacman: PacmanComponent, state: DeltaState): Boolean = pacman.moveLeft(state)
}

case object RightMoving extends MovingState {
  def movingOn(pacman: PacmanComponent, state: DeltaState): Boolean = pacman.moveRight(state)
}

case object UpMoving extends MovingState {
  def movingOn(pacman: PacmanComponent, state: DeltaState): Boolean = pacman.moveUp(state)
}

case object DownMoving extends MovingState {
  def movingOn(pacman: PacmanComponent, state: DeltaState): Boolean = pacman.moveDown(state)
}