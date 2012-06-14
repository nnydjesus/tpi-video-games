package ar.edu.unq.tpi
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.edu.unq.tpi.resource.TraitResources

abstract class Player(tag:String) extends TraitResources{
  
  val controls = xmlFromFile("controls/controllers.xml")
  var thisControls = controls\ tag
  
  var LEFT = Key.fromCode((thisControls\"LEFT").text)
  var RIGHT = Key.fromCode((thisControls\"RIGHT").text)
  var UP = Key.fromCode((thisControls\"UP").text)
  var DOWN = Key.fromCode((thisControls\"DOWN").text)
  var ENTER = Key.fromCode((thisControls\"ENTER").text)
  
  var K_HIGH_KICK1 = Key.fromCode((thisControls\"K_HIGH_KICK1").text)
  var K_LOW_KICK1 = Key.fromCode((thisControls\"K_LOW_KICK1").text)
  var K_HIGH_KICK2 = Key.fromCode((thisControls\"K_HIGH_KICK2").text)
  var K_LOW_KICK2 = Key.fromCode((thisControls\"K_LOW_KICK2").text)
  var K_HIGH_PUCH1 = Key.fromCode((thisControls\"K_HIGH_PUCH1").text)
  var K_LOW_PUNCH1 = Key.fromCode((thisControls\"K_LOW_PUNCH1").text)
  var K_HIGH_PUCH2 = Key.fromCode((thisControls\"K_HIGH_PUCH2").text)
  var K_LOW_PUNCH2 = Key.fromCode((thisControls\"K_LOW_PUNCH2").text)

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
      //dispatchEvent(new Event(GameEvents.MOVE_CABEZAL_UP, this, this.orientation))
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

case class Player1 extends Player("Player1") {}
case class Player2 extends Player("Player2"){}

class PlayerCPU extends Player("Player2") {

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