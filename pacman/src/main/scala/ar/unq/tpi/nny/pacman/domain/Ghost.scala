package ar.unq.tpi.nny.pacman.domain;
import ar.unq.tpi.nny.pacman.domain.Controller
import ar.unq.tpi.nny.pacman.domain.GhostController

abstract class Ghost private (override var controller:Controller) extends AiControlledCreature {
  

	def this(startPosition:Position, currentDirection:Direction, ghostController:GhostController) {
		this(ghostController)
		
		this.startPosition = startPosition;
		currentPosition = startPosition.clone();
		this.currentDirection = currentDirection;
		this.points = POINTS;
//		setInvulnerable();
		vulnerable = false
	}

	def getNextDirection() =  controller.move();

	def getNextPosition():Position ={
		var position = currentPosition.clone();
		var newDirection = getNextDirection();
		var nextPosition = getRelativePosition(newDirection);
		
		position.addToPosition(nextPosition);
		currentDirection = newDirection;
		return position;
	}

	override def toString() = "G";

}
