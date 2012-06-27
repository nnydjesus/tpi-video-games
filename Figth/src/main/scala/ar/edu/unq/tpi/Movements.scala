package ar.edu.unq.tpi
import com.uqbar.vainilla.DeltaState

//object Movements extends Enumeration {
// type Movements = Value
//  var IDLE, WALK, WALK_BACK, HIGH_KICK1, LOW_KICK1, HIGH_KICK2, LOW_KICK2, HIGH_PUCH1, LOW_PUNCH1, HIGH_PUCH2, LOW_PUNCH2, JUMP = Value //COMBO1, COMBO2, COMBO3, COMBO4 = Value
//
//}

abstract class Movement {
  def update(character: CharacterFight, deltaState: DeltaState, superUpdate:(DeltaState)=>Unit) {
    if (character.isMoving) {
      character.isMoving = !character.getAppearance().finish()
      character.damageOponent()
      superUpdate(deltaState)
      if (!character.isMoving) {
        character.getAppearance().reset()
        character.changeMove(IDLE)
      }
    } else {
      if (!character.player.update(deltaState)) {
        superUpdate(deltaState)
      }
      character.updateOrientationRelativeToOponent()
    }
    
    restorePosition(character)

  }
  
  def restorePosition(character: CharacterFight){
      character.setY(character.baseY)
  }
}

class AttackMovement() extends Movement {
  override def update(character: CharacterFight, deltaState: DeltaState, superUpdate:(DeltaState)=>Unit) {
	character.attack(this)(deltaState)
    super.update(character, deltaState, superUpdate)
  }
}

case object SELECTED extends Movement {
}

case object IDLE extends Movement {
}

case object WALK extends Movement {
}

case object WALK_BACK extends Movement {
}

case object KICKED extends Movement {
}

case object HIGH_KICK1 extends AttackMovement
case object LOW_KICK1 extends AttackMovement
case object HIGH_KICK2 extends AttackMovement

case object HIGH_PUCH1 extends AttackMovement

case object HIGH_PUCH2 extends AttackMovement

case object LOW_PUNCH2 extends AttackMovement

case object LOW_PUNCH1 extends AttackMovement

case object LOW_KICK2 extends AttackMovement

case object JUMP extends Movement {

  override def update(character: CharacterFight, deltaState: DeltaState, superUpdate:(DeltaState)=>Unit) {
    if (character.inUp && character.getY() > 0) {
      character.isMoving = true
      character.inUp = true
      character.move(0, -30)
      character.getAppearance().setCurrentIndex(0)
    } else {
      if (character.getY() < character.baseY) {
        character.inUp = false
        character.isMoving = true
        character.move(0, 30)
        character.getAppearance().setCurrentIndex(1)
      } else {
        //        character.getAppearance().advance()
        character.isMoving = false
        character.inUp = true
        character.changeMove(IDLE)
      }
    }
    
  }
  
  override def restorePosition(character: CharacterFight){}
}

case object COMBO1 extends AttackMovement

object Orientation extends Enumeration {
  type Orientation = Value
  var LEFT, RIGHT = Value

}