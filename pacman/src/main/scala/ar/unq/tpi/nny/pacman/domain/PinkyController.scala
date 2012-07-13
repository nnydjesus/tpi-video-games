package ar.unq.tpi.nny.pacman.domain;
import ar.unq.tpi.nny.pacman.domain.GhostController

class PinkyController extends GhostController {


	def move():Direction = {
		var directionToPacman = directionToPacmanUsingPosition();

		if (directionToPacman != null) {
			return directionToPacman;
		} else if (!canGoStraight(aiControlledCreatureDirection)) {
			return getRandomDirection();
		} else {
			return aiControlledCreatureDirection;
		}
	}
}
