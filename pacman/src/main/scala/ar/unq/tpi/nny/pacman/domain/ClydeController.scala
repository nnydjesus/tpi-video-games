package ar.unq.tpi.nny.pacman.domain;
import ar.unq.tpi.nny.pacman.domain.GhostController

class ClydeController extends GhostController {

	val DEPTH = 0;

	override def move():Direction = {
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
