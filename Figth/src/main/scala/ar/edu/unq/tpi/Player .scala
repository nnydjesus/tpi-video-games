package ar.edu.unq.tpi
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.traits.FunctionSceneListener

abstract class Player {
  var LEFT: Key
  var RIGHT: Key
  var UP: Key
  var DOWN: Key

  var K_HIGH_KICK1: Key
  var K_LOW_KICK1: Key
  var K_HIGH_KICK2: Key
  var K_LOW_KICK2: Key
  var K_HIGH_PUCH1: Key
  var K_LOW_PUNCH1: Key
  var K_HIGH_PUCH2: Key
  var K_LOW_PUNCH2: Key

  //  var COMBO1 = Array(Key.T)
  //  var COMBO2 = Array(Key.T)
  //  var COMBO3 = Array(Key.T)
  //  var COMBO4 = Array(Key.T)

  var _character: CharacterFight = null

  def character = this._character
  def character_=(anCharacter: CharacterFight) = {
    this._character = anCharacter
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_KICK1, new FunctionSceneListener((d)=>character.changeMove(HIGH_KICK1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_KICK2, new FunctionSceneListener((d)=>character.changeMove(HIGH_KICK2)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_KICK1, new FunctionSceneListener((d)=>character.changeMove(LOW_KICK1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_KICK2, new FunctionSceneListener((d)=>character.changeMove(LOW_KICK2)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_PUNCH1, new FunctionSceneListener((d)=>character.changeMove(LOW_PUNCH1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_HIGH_PUCH1, new FunctionSceneListener((d)=>character.changeMove(HIGH_PUCH1)))
    anCharacter.scene.addKeyPressetListener(anCharacter, this.K_LOW_PUNCH2, new FunctionSceneListener((d)=>character.changeMove(LOW_PUNCH2)))
  }

  def update(deltaState: DeltaState): Boolean = {
    if (deltaState.isKeyPressed(this.UP)) {
      character.changeMove(JUMP)
      return false;
    }
    if (deltaState.isKeyBeingHold(this.LEFT) && deltaState.isKeyBeingHold(this.RIGHT) && deltaState.isKeyBeingHold(this.K_HIGH_PUCH1)) {
      character.attack(COMBO1)(deltaState)
    } else if (deltaState.isKeyBeingHold(this.LEFT)) {
      character.walkLeft(deltaState)
    } else if (deltaState.isKeyBeingHold(this.RIGHT)) {
      character.walkRight(deltaState)
    } else {
      character.changeMove(IDLE)
      return false;
    }

    return true

  }
}
class Player1 extends Player {

  var LEFT = Key.A
  var RIGHT = Key.D
  var UP = Key.W
  var DOWN = Key.S

  var K_HIGH_KICK1 = Key.T
  var K_LOW_KICK1 = Key.Y
  var K_HIGH_KICK2 = Key.U
  var K_LOW_KICK2 = Key.I
  var K_HIGH_PUCH1 = Key.G
  var K_LOW_PUNCH1 = Key.H
  var K_HIGH_PUCH2 = Key.J
  var K_LOW_PUNCH2 = Key.K

}

class Player2 extends Player {

  var LEFT = Key.LEFT
  var RIGHT = Key.RIGHT
  var UP = Key.UP
  var DOWN = Key.DOWN

  var K_HIGH_KICK1 = Key.NUMPAD9
  var K_LOW_KICK1 = Key.NUMPAD8
  var K_HIGH_KICK2 = Key.NUMPAD7
  var K_LOW_KICK2 = Key.NUMPAD6
  var K_HIGH_PUCH1 = Key.NUMPAD5
  var K_LOW_PUNCH1 = Key.NUMPAD4
  var K_HIGH_PUCH2 = Key.NUMPAD2
  var K_LOW_PUNCH2 = Key.NUMPAD1

}

class PlayerCPU extends Player {
  var LEFT = Key.LEFT
  var RIGHT = Key.RIGHT
  var UP = Key.UP
  var DOWN = Key.DOWN

  var K_HIGH_KICK1 = Key.NUMPAD9
  var K_LOW_KICK1 = Key.NUMPAD8
  var K_HIGH_KICK2 = Key.NUMPAD7
  var K_LOW_KICK2 = Key.NUMPAD6
  var K_HIGH_PUCH1 = Key.NUMPAD5
  var K_LOW_PUNCH1 = Key.NUMPAD4
  var K_HIGH_PUCH2 = Key.NUMPAD2
  var K_LOW_PUNCH2 = Key.NUMPAD1

  override def update(deltaState: DeltaState): Boolean = {
    if ((Math.random * 100) >= 50) {
      character.changeMove(IDLE)
      return false

    } else {

      if (!character.collidesWith(character.oponent)) {
        //        character.walkToOponent(deltaState)
      } else {
        var buffer = Buffer[Movement]()
        //        buffer.appendAll(Array[Movement](HIGH_KICK1, HIGH_KICK2, HIGH_PUCH1, HIGH_PUCH2, LOW_KICK1, LOW_KICK2, LOW_PUNCH1, LOW_PUNCH2, WALK, WALK_BACK))
        //        character.attack(buffer((Math.random * (buffer.length - 1)).toInt))
      }
      return true;

    }
  }

}