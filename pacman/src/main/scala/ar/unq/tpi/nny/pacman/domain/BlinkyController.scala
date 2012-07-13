package ar.unq.tpi.nny.pacman.domain;
import ar.unq.tpi.nny.pacman.domain.GhostController
import ar.unq.tpi.nny.pacman.domain.TraversableBlock

class BlinkyController extends GhostController {

  val DEPTH = Integer.MAX_VALUE;
  
  override def move(): Direction = {
    var directionToPacman = directionToPacmanUsingNeighbors(DEPTH);

    if (directionToPacman != null) {
      return directionToPacman;
    } else if (!canGoStraight(aiControlledCreatureDirection)) {
      return getRandomDirection();
    } else {
      return aiControlledCreatureDirection;
    }
  }
}
