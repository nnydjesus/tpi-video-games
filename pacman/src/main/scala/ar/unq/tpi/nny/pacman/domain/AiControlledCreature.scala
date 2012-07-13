package ar.unq.tpi.nny.pacman.domain

import scala.collection.JavaConversions._
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import ar.unq.tpi.nny.pacman.domain.Controller

trait AiControlledCreature extends Creature {

  val POINTS = 200;
  val DEFAULT_DIRECTION = Direction.UP;

  var controller: Controller
  
  def setupController(neighbors: Map[Direction, Buffer[Block]], pacmanPosition: Position) {
    controller.setUp(neighbors, pacmanPosition, currentPosition,
      currentDirection, vulnerable);
  }

  def reset() {
    this.setInVulnerable()
    this.currentPosition = startPosition
    this.currentDirection = DEFAULT_DIRECTION
    this.setAlive();
  }

  def isControlledByPlayer() = false;

}
