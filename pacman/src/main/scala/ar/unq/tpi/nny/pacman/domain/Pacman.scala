package ar.unq.tpi.nny.pacman.domain;
import scala.collection.mutable.Buffer

class Pacman extends PlayerControlledCreature {

  def this(startPosition: Position, currentDirection: Direction) {
    this()
    this.startPosition = startPosition
    this.currentDirection = currentDirection
    this.name = "pacman"
    reset()
  }

  def reset() {
    setVulnerable();
    currentPosition = startPosition
    dead = false
  }

  def setToDead() {
    dead = true
    setVulnerable();
  }

  def updateStatus(ghost: Ghost) {
    if (!ghost.vulnerable) {
      setToDead();
    } else {
      ghost.setDead()
      ghost.setVulnerable()
    }
  }
  
  def updateTimer(cant: Double){
    if(!this.vulnerable){
    	decrementTimer(cant);
    }
  }

  def eatCurrentCreatures() {
    creaturesOnCurrentFloor.foreach(creature => {
      if (creature != this) {
        creature.setDead();
      }
    })
  }

  def handleCurrentCreatures(): Int = {
    var totalPointsToAdd = 0;

    if (!vulnerable) {
      creaturesOnCurrentFloor.foreach(creature => {
        if (creature != this) {
          totalPointsToAdd += creature.points
        }

      })
    }
    return totalPointsToAdd;
  }

  def handleCurrentItems(): Int = {
    var totalPointsToAdd = 0;
    
    var itemsProcess = Buffer[Item]()

    currentItems.foreach(item => {
      totalPointsToAdd += handleItem(item);
      itemsProcess.append(item);
    })
    
    itemsProcess.foreach(item => currentItems-=(item))

    return totalPointsToAdd;
  }

  def handleItem(item: Item): Int = {
    if (item != null) {
      return item.doAction(this);
    } else {
      return 0;
    }
  }

  override def toString() = "PAC"
}
