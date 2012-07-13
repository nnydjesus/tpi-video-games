package ar.unq.tpi.nny.pacman.domain

trait PlayerControlledCreature extends Creature {

  val POINTS = 0;

  def isControlledByPlayer() = true

  def getNextPosition(): Position = {
    var position = currentPosition.clone();
    position.addToPosition(getRelativePosition(currentDirection));
    return position;
  }

  def handlePoints() = handleCurrentCreatures() + handleCurrentItems();

  def handleCurrentCreatures():Int;

  def handleCurrentItems():Int;

}
