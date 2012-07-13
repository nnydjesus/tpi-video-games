package ar.unq.tpi.nny.pacman.domain

package ar.unq.tpi.nny.pacman.domain;

import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scala.util.Random


trait Controller {

  val random = new Random()
  val directions = Buffer[Direction]()

  var neighbors: Map[Direction, Buffer[Block]] = _
  var playerControlledCreaturePosition: Position = _
  var aiControlledCreaturePosition: Position = _
  var aiControlledCreatureDirection: Direction = _
  var aiControlledCreatureVulnerable: Boolean = _

  this.fillDirections();

  def setUp(
    neighbors: Map[Direction, Buffer[Block]],
    playerControlledCreaturePosition: Position,
    aiControlledCreaturePosition: Position,
    aiControlledCreatureDirection: Direction,
    aiControlledCreatureVulnerable: Boolean) {

    this.neighbors = neighbors;
    this.playerControlledCreaturePosition = playerControlledCreaturePosition;
    this.aiControlledCreaturePosition = aiControlledCreaturePosition;
    this.aiControlledCreatureDirection = aiControlledCreatureDirection;
    this.aiControlledCreatureVulnerable = aiControlledCreatureVulnerable;
  }

  def canGoStraight(direction: Direction): Boolean = {
    return neighbors.get(direction).get(0) != null && neighbors.get(direction).get(0).isTraversable()
  }


  def fillDirections() {
    directions.append(Direction.UP);
    directions.append(Direction.DOWN);
    directions.append(Direction.LEFT);
    directions.append(Direction.RIGHT);
  }

  def move(): Direction;
}
