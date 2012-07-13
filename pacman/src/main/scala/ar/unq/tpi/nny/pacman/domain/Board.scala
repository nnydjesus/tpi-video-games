package ar.unq.tpi.nny.pacman.domain;

import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.mutable.Map
import ar.unq.tpi.nny.pacman.domain.TraversableBlock
import event.EventGame
import event.DEvent

class Board(var blocks: Matrix[Block], var creatures: Buffer[Creature], var pacman: Pacman) extends EventGame[Board] {

  val CHERRY_APPEAR = 5;
  val CHERRY_DISAPPEAR = 20;
  val CHERRY_POSITION = new Position(18, 15);

  var directionToPosition = Map[Direction, Position]()
  var counter = 0
  val cherry = new Cherry()

  initializeBoard();

  protected def initializeBoard() {
    fillDirectionToPosition();
    initializeCreatures();
  }
  
  protected def fillDirectionToPosition() {
    directionToPosition(Direction.DOWN) = new Position(0, 1)
    directionToPosition(Direction.UP) = new Position(0, -1)
    directionToPosition(Direction.RIGHT) = new Position(1, 0)
    directionToPosition(Direction.LEFT) = new Position(-1, 0)
    directionToPosition(Direction.STOP) = new Position(0, 0)
  }

  protected def initializeCreatures() {
    fillBlocksWithCreatures();
    setCreatureDirectionToPosition();
    setCreaturesItems();
    setCreaturesOnSameFloor();
    setNeighborsOfAiControlledCreature();
  }

  protected def setCreatureDirectionToPosition() {
    creatures.foreach(creature => {
      creature.directionToPosition = this.directionToPosition
    })
  }

  protected def fillBlocksWithCreatures() {
    creatures.foreach(creature => {
      var block = getBlock(creature.currentPosition)
      if (block != null && block.isTraversable()) {
        block match {
          case traversableBlock: TraversableBlock => traversableBlock.addCreature(creature)
        }
      }
    })
  }

  def getBlock(position: Position) = blocks(position.row.toInt)(position.column.toInt);

  def isBlockValid(position: Position): Boolean = {
    var row = position.row.toInt
    var column = position.column.toInt

    if (row >= 0 && row < width && column < height && column >= 0) {
      return getBlock(position).isTraversable()
    } else {
      return false;
    }
  }

  def isPositionValid(position: Position): Boolean = {
    var row = position.row
    var column = position.column

    return (row >= 0 && row < this.width && column < this.height && column >= 0)
  }

  def width = blocks.width
  def height = blocks.height

  def updateTraversableBlock(oldBlock: TraversableBlock, newBlock: TraversableBlock, creature: Creature) {
    if (oldBlock != null) {
      oldBlock.removeCreature(creature);
    }
    newBlock.addCreature(creature);
  }

  protected def setCreaturesOnSameFloor() {
    creatures.map(setCreaturesOnSameFloor)
  }

  protected def setCreaturesOnSameFloor(creature: Creature) {
    if (getBlockOfCreature(creature) != null) {
      var creaturesOnSameFloor = this.getBlockOfCreature(creature).creatures;
      creature.creaturesOnCurrentFloor = creaturesOnSameFloor
    }
  }

  def setCreaturesItems() {
    creatures.foreach(creature => {
      var items = getBlockOfCreature(creature).items;
      creature.currentItems = items;
    })
  }

  def collisionHappened(): Boolean = {
    var blockOfCreature = getBlockOfCreature(this.pacman);
    if (blockOfCreature == null) {
      return false;
    }
    return blockOfCreature.collisionHappened();
  }

  def getBlockOfCreature(creature: Creature): TraversableBlock = {
    getBlock(creature.currentPosition) match {
      case t: TraversableBlock => t
      case _ => null
    }
  }

  def step() {
    //    updateCreatures();
    setDeadAiControlledCreaturesToStartPosition();
    setCreaturesItems();
    //    setCreaturesOnSameFloor();
    setNeighborsOfAiControlledCreature();
    updateAiControlledCreaturesStatus()
    updateCounter();
  }
  

  def updateCounter() {
    counter += 1
    if (counter == CHERRY_APPEAR) {
      placeCherry();
    }
    if (counter == CHERRY_DISAPPEAR) {
      removeCherry();
      counter = 0
    }
  }

  def placeCherry() {
    var block = getBlock(CHERRY_POSITION);

    block match {
      case traversableBlock: TraversableBlock => traversableBlock.addItem(cherry);
    }

    dispatchEvent(new DEvent("PLACE_CHERRY", this))
  }

  def removeCherry() {
    var block = getBlock(CHERRY_POSITION);

    block match {
      case traversableBlock: TraversableBlock => traversableBlock.removeItem(cherry);
    }
    dispatchEvent(new DEvent("REMOVE_CHERRY", this))
  }

  def updateCreatures() {
    //    if (counter % 2 == 0) {
    updateAllCreatures();
    //    } else {
    //      updateInvulnerableCreatures();
    //    }
  }

  def updateAllCreatures() {
    creatures.map(updateCreature)
  }

  def updateCreature(creature: Creature) {
      var position = creature.getNextPosition();
      if (isBlockValid(position)) {
        var oldBlock: TraversableBlock = getBlockOfCreature(creature);
        getBlock(position) match {
          case newBlock: TraversableBlock => {
            updateTraversableBlock(oldBlock, newBlock, creature);
            creature.currentPosition = position
          }
        }
      }
    setCreaturesOnSameFloor(creature)
  }

  def updateInvulnerableCreatures() {
    creatures.foreach(creature => {
      if (!creature.vulnerable || creature.isControlledByPlayer()) {
        var position = creature.getNextPosition();
        if (isBlockValid(position)) {
          var oldBlock: TraversableBlock = getBlockOfCreature(creature);
          getBlock(position) match {
            case newBlock: TraversableBlock => {
              updateTraversableBlock(oldBlock, newBlock, creature);
              creature.currentPosition = position
            }
          }
        }
        if (collisionHappened()) {
          //					break;
        }
      }
    })
  }

  def updateAiControlledCreaturesStatus() {
    if (!this.pacman.vulnerable) {
      creatures.foreach(creature => {
        if (!creature.isControlledByPlayer()
          && !creature.vulnerable) {
          creature.setVulnerable();
        }
      })
    } else {
      creatures.foreach(creature => {
        if (!creature.isControlledByPlayer() && creature.vulnerable) {
          creature.setInVulnerable()
        }
      })
    }
  }

  def setDeadAiControlledCreaturesToStartPosition() {
    creatures.foreach(creature => {
      if (!creature.isControlledByPlayer() && creature.dead) {
        var oldBlock = getBlockOfCreature(creature);
        creature.reset();
        var newBlock = getBlockOfCreature(creature);
        updateTraversableBlock(oldBlock, newBlock, creature);
      }
    })
  }

  def restart() {
    setCreaturesToStartPosition();
    updateAiControlledCreaturesStatus();
    setCreaturesItems();
    //    setCreaturesOnSameFloor();
    setNeighborsOfAiControlledCreature();
    pacman.reset()
  }

  def setCreaturesToStartPosition() {
    creatures.foreach(creature => {
      var oldBlock: TraversableBlock = getBlockOfCreature(creature);
      creature.reset();
      var newBlock: TraversableBlock = getBlockOfCreature(creature);
      updateTraversableBlock(oldBlock, newBlock, creature);
    })
  }

  def setNeighborsOfAiControlledCreature() {
    creatures.foreach(creature => {
      if (!creature.isControlledByPlayer()) {
        creature match {
          case aiControlCreature: AiControlledCreature => {
            var aiCreaturePosition = aiControlCreature.currentPosition;
            aiControlCreature.setupController(getNeighborBlocks(aiCreaturePosition), this.pacman.currentPosition);
          }
        }
      }
    })
  }

  def getNeighborBlocks(position: Position): Map[Direction, Buffer[Block]] = {
    var neighbors = Map[Direction, Buffer[Block]]();

    directionToPosition.keySet.foreach(direction => {
      if (direction != Direction.STOP) {
        var newPosition = position.clone();
        var relativePosition = directionToPosition(direction).clone();
        newPosition.addToPosition(relativePosition);
        var directionNeighbors = Buffer[Block]();
        while (isPositionValid(newPosition)) {
          directionNeighbors.append(getBlock(newPosition));
          newPosition.addToPosition(relativePosition);
        }
        neighbors(direction) = directionNeighbors
      }
    })

    return neighbors;
  }

  def noMoreItemsOnBoard(): Boolean = {

    blocks.elems.values.foreach(row => {
      row.values.foreach(block => {
        if (block.containsItem()) {
          return false
        }
      })
    })

    return true;
  }

  override def toString(): String = {
    var boardString = "";

    blocks.elems.values.foreach(row => {
      row.values.foreach(block => boardString += block.toString())
      boardString += "\n";
    })

    return boardString;
  }
}