package ar.unq.tpi.nny.pacman.domain

import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import event.EventGame
import event.DEvent
import com.uqbar.vainilla.events.GameEvent

trait Creature extends EventGame[Creature] {

  var startPosition: Position = _
  var points: Int = _

  var directionToPosition: Map[Direction, Position] = _
  var currentPosition: Position = _
  var currentDirection: Direction = _
  var creaturesOnCurrentFloor: Buffer[Creature] = Buffer()
  var currentItems: Buffer[Item] = Buffer()
  var name: String = _
  var dead = false
  var vulnerable = false
  val MAINING_TIME = 5D
  var timer = MAINING_TIME 

  def getRelativePosition(direction: Direction): Position = directionToPosition(direction);

  def setDead() = dead = true;

  def setAlive() = dead = false;

  def setVulnerable() = vulnerable = true
  def setInVulnerable() = vulnerable = false

  def currentCreaturesContainsAiControlledCreature(): Boolean = {
    creaturesOnCurrentFloor.foreach(creature => {
      if (!creature.isControlledByPlayer()) {
        return true;
      }
    })

    return false;
  }

  def currentAiControlledCreaturesAreVulnerable(): Boolean = {
    creaturesOnCurrentFloor.foreach(creature => {
      if (!creature.isControlledByPlayer() && !creature.dead && !creature.vulnerable) {
        return false;
      }
    })

    return true;
  }

  def resetTimer() = timer = 0

  var timerIsRunningOut: () => Unit = _

  def decrementTimer(cant: Double) {
    if (timer > 0) {
      timer -= cant;
    } else {
      timer = MAINING_TIME
      setVulnerable()
      timerIsRunningOut()
    }
  }

  def isControlledByPlayer(): Boolean

  def getNextPosition(): Position;

  def reset();

}
