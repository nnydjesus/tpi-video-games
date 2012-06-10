package ar.edu.unq.tpi
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.FunctionEvent
import ar.edu.unq.tpi.traits.RoundComponent
import ar.unq.tpi.components.SpriteComponent
import ar.unq.tpi.components.GenericGameEvents

class CharacterFight(player: Player, var character: Character, var scene: GamePlayScene, x: Int, staticY: Int) extends Actor[GamePlayScene, CharacterFight](null, x, staticY) {

  player.character = this

  var oponent: CharacterFight = null

  var orientation = Orientation.LEFT
  var delta = 500
  var currentMove: Movement = IDLE
  var pego = true

  changeMove(IDLE)

  def changeMove(move: Movement, orientation: Orientation.Orientation) = {
    this.currentMove = move
    this.setAppearance(character.getMove(move).getAnimation(orientation))
  }

  override def changeMove(move: Movement) = {
    if(!isMoving){
    	changeMove(move, orientation)
    }
  }

  def walk() = {
    var move: Movement = null
    if (orientation.equals(Orientation.LEFT)) {
      move = WALK
    } else {
      move = WALK_BACK
    }
    changeMove(move)
  }

  def receiveAttack(force: Double) {
    character.receiveAttack(force)
    if (this.orientation.equals(Orientation.LEFT)) {
      this.move(force, 0)
    } else {
      this.move(-force, 0)
    }
    
    changeMove(KICKED)
    isMoving = true

    if (this.character.death) {
      dispatchEvent(new Event(GameEvents.DEATH, this))
    }
  }

  def attack(attack: Movement)(delta: DeltaState) {
    if (!isMoving) {
//      this.changeMove(attack)
      isMoving = true;
      pego = false
      damageOponent()
    }
  }

  def damageOponent() {
    if (!pego && collidesWith(oponent)) {
      pego = true
      val force = character.getMove(currentMove).force
      oponent.receiveAttack(force)
      this.character.addScore(20*force.toFloat)
      
      var spriteX= this.getX() + positionOnCollition._1 - (GameImage.COLLITION.getWidth()/2)
      var spriteY= this.getY() + positionOnCollition._2 - (GameImage.COLLITION.getHeight()/2)
      var sprite = new SpriteComponent[GamePlayScene](GameImage.COLLITION, spriteX, spriteY) with RoundComponent[GamePlayScene, Sprite]{override val meantime =1D}
      sprite.addEventListener(GenericGameEvents.FINISH_ANIMATION, new FunctionEvent((e:Event[RoundComponent[GamePlayScene, Sprite], Any]) => sprite.destroy()))
      scene.addComponent(sprite)
      
    }
  }

  override def update(deltaState: DeltaState) = {

  currentMove.update(this, deltaState)
    if (isMoving) {
      isMoving = !this.getAppearance().finish()
      damageOponent()
      super.update(deltaState)
      if (!isMoving) {
        this.getAppearance().reset()
        changeMove(IDLE)
      }
    } else {
      if (!player.update(deltaState)) {
        super.update(deltaState)
      }
      updateOrientationRelativeToOponent()
    }
    
    
    if (currentMove != JUMP) {
    	this.setY(baseY)
    }

  }
  
  def updateOrientationRelativeToOponent() {
    if (oponent.getX() > this.getX()) {
      orientation = Orientation.RIGHT
    } else {
      orientation = Orientation.LEFT
    }
  }

  def baseY = staticY - this.getAppearance().getHeight() + 120

  def walkLeft(deltaState: DeltaState) {
    val moveX = -delta * deltaState.getDelta()
    this.move(moveX, 0)
    if (orientation.equals(Orientation.LEFT)) {
      this.changeMove(WALK)

    } else {
      this.changeMove(WALK_BACK)
    }
    this.getAppearance().advance()

    isMoving = false;
    if (this.collideWithBoundLeft ) {
      if(!oponent.collideWithBoundRigth){
    	  dispatchEvent(new Event(GameEvents.COLLIDE_WITH_BOUND_LEFT, this, this.orientation))
      }
      this.move(-moveX, 0)
    }

    if (collidesWith(oponent)) {
      this.move(-moveX, 0)
    }
  }

  def walkRight(deltaState: DeltaState) {
    val moveX = delta * deltaState.getDelta()
    this.move(moveX, 0)
    if (orientation.equals(Orientation.LEFT)) {
      this.changeMove(WALK_BACK)
    } else {
      this.changeMove(WALK)
    }
    this.getAppearance().advance()
    isMoving = false;

    if (this.collideWithBoundRigth) {
      if(!oponent.collideWithBoundLeft){
    	  dispatchEvent(new Event(GameEvents.COLLIDE_WITH_BOUND_RIGTH, this, this.orientation))
      }
      this.move(-moveX, 0)
    }
    if (collidesWith(oponent)) {
      this.move(-moveX, 0)
    }
  }
  
  def collideWithBoundRigth = collidesWith(scene.boundRigth)
  def collideWithBoundLeft = collidesWith(scene.boundLeft)

  def walkToOponent(deltaState: DeltaState) {
    if (this.getX() > oponent.getX()) {
      walkLeft(deltaState)
    } else {
      walkRight(deltaState)
    }

  }

  var deltaWidth = 300
  var deltaHeight = 150

}