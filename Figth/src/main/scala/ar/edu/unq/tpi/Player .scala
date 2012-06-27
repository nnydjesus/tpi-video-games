package ar.edu.unq.tpi
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.traits.FunctionSceneListener
import ar.edu.unq.tpi.resource.TraitResources
import scala.xml.NodeSeq
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import scala.xml.Elem
import java.io._
import scala.io._
import java.io.File
import  ar.edu.unq.tpi.RichFile._


abstract class  Player(file:String) extends TraitResources{
 var fileName = "controls/" + file + ".xml"
  var thisControls = xmlFromFile(fileName)

  var LEFT = new PlayerKey("LEFT", thisControls)
  var RIGHT = new PlayerKey("RIGHT", thisControls)
  var UP = new PlayerKey("UP", thisControls)
  var DOWN = new PlayerKey("DOWN", thisControls)
  var ENTER = new PlayerKey("ENTER", thisControls)

  var K_HIGH_KICK1 = new PlayerKey("K_HIGH_KICK1", thisControls)
  var K_LOW_KICK1 = new PlayerKey("K_LOW_KICK1", thisControls)
  var K_HIGH_KICK2 = new PlayerKey("K_HIGH_KICK2", thisControls)
  var K_LOW_KICK2 = new PlayerKey("K_LOW_KICK2", thisControls)
  var K_HIGH_PUCH1 = new PlayerKey("K_HIGH_PUNCH1", thisControls)
  var K_LOW_PUNCH1 = new PlayerKey("K_LOW_PUNCH1", thisControls)
  var K_HIGH_PUCH2 = new PlayerKey("K_HIGH_PUNCH2", thisControls)
  var K_LOW_PUNCH2 = new PlayerKey("K_LOW_PUNCH2", thisControls)

  val keys = List(LEFT, RIGHT, UP, DOWN, ENTER, K_HIGH_KICK1, K_HIGH_KICK2, K_HIGH_PUCH1, K_HIGH_PUCH2, K_LOW_KICK1, K_LOW_KICK2, K_LOW_PUNCH1, K_LOW_PUNCH2)

  //  var COMBO1 = Array(Key.T)
  //  var COMBO2 = Array(Key.T)
  //  var COMBO3 = Array(Key.T)
  //  var COMBO4 = Array(Key.T)


  var _character: CharacterFight = null
  
  implicit def functionListener(f:(DeltaState) => Unit) = new FunctionSceneListener(f)

  def character = this._character
  def character_=(anCharacter: CharacterFight) = {
    this._character = anCharacter
	anCharacter.scene.addKeyBeingHoldListener(anCharacter, new FunctionSceneListener(character.attack(COMBO1)), this.LEFT.key, this.RIGHT.key, this.K_HIGH_PUCH1.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(HIGH_KICK1), this.K_HIGH_KICK1.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(HIGH_KICK2), this.K_HIGH_KICK2.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(LOW_KICK1), this.K_LOW_KICK1.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(LOW_KICK2), this.K_LOW_KICK2.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(LOW_PUNCH1), this.K_LOW_PUNCH1.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(HIGH_PUCH1), this.K_HIGH_PUCH1.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(LOW_PUNCH2), this.K_LOW_PUNCH2.key)
    anCharacter.scene.addKeyPressetListener(anCharacter, (d:DeltaState) => character.changeMove(JUMP), this.UP.key)
    anCharacter.scene.addKeyBeingHoldListener(anCharacter, (d:DeltaState) => character.walkLeft(d), this.LEFT.key)
    anCharacter.scene.addKeyBeingHoldListener(anCharacter, (d:DeltaState) => character.walkRight(d), this.RIGHT.key)
  }

  def update(deltaState: DeltaState): Boolean = {
  if (deltaState.isKeyBeingHold(this.LEFT.key)) {
    } else if (deltaState.isKeyBeingHold(this.RIGHT.key)) {
    } else {
      character.changeMove(IDLE)
      return false;
    }

    return true

  }
  
  def saveConf(){
    var conf =
    	<Control>{
            	for (key <- keys) yield <key id={ key.name} key={key.key.getCode()}> </key>
    	}</Control>
    
    new File(PATH + fileName).text = conf.toString()
  }
  
}

object Player1 extends Player("controlPlayer1") {}
object Player2 extends Player("controlPlayer2") {}

object PlayerCPU extends Player("controlPlayer2") {

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

class PlayerKey(var name: String, node: NodeSeq) extends EventGameComponent[PlayerKey] {
  var conf = (node \"key") find { n=> (n \ "@id" ).text == name }
  var key = Key.fromCode((conf.get \ "@key").text)

  def setKey(k: Key) = {
    key = k
    dispatchEvent(new Event("key", this, k))
  }

  def save(node: NodeSeq, futureKey: Key) {
    key = futureKey
  }
}
