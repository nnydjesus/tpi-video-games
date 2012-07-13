package ar.unq.tpi.nny.pacman.domain

package ar.unq.tpi.nny.pacman.domain;

import java.util.ArrayList;

trait GhostController extends Controller {

  def directionToPacmanUsingNeighbors(depth: Int): Direction = {
    var newDirection = computeDirectionToPacmanUsingNeighbors(depth);

    if (newDirection != null) {
      if (!this.aiControlledCreatureVulnerable) {
        if (canGoStraight(newDirection)) {
          return newDirection;
        } else {
          return null;
        }
      } else {
        var oppositeDirection = Direction.getOppositeDirection(newDirection);
        if (canGoStraight(oppositeDirection)) {
          return oppositeDirection;
        } else {
          return null;
        }
      }
    } else {
      return null;
    }
  }

  def computeDirectionToPacmanUsingNeighbors(depth: Int): Direction = {
    directions.foreach(direction => {
      var blocks = this.neighbors(direction);
      var min = Math.min(depth, blocks.size);

      for (i <- 0 to min by 1 if i < min) {
        var block = blocks(i);
        if (block != null && block.isTraversable()) {
          block match {
            case traversableBlock: TraversableBlock => {
              if (traversableBlock.containsPacman()) {
                return direction;
              }
            }

          }
        }
      }
    })

    return null;
  }

  def directionToPacmanUsingPosition(): Direction = {
    var newDirection = computeDirectionToPacmanUsingPosition();
    if (newDirection != null) {
      if (!this.aiControlledCreatureVulnerable) {
        return newDirection;
      } else {
        if (canGoStraight(Direction.getOppositeDirection(newDirection))) {
          return Direction.getOppositeDirection(newDirection);
        } else {
          return null;
        }
      }
    } else {
      return null;
    }
  }

  def computeDirectionToPacmanUsingPosition(): Direction = {
    var monsterColumn = aiControlledCreaturePosition.column
    var monsterRow = aiControlledCreaturePosition.row

    var pacmanColumn = playerControlledCreaturePosition.column
    var pacmanRow = playerControlledCreaturePosition.row

    if (neighbors(Direction.DOWN).size > 0 && pacmanColumn > monsterColumn && neighbors(Direction.DOWN)(0).isTraversable()) {
      return Direction.DOWN;
    } else if (neighbors(Direction.UP).size > 0 && pacmanColumn < monsterColumn && neighbors(Direction.UP)(0).isTraversable()) {
      return Direction.UP;
    } else if (neighbors(Direction.RIGHT).size > 0 && pacmanRow > monsterRow && neighbors(Direction.RIGHT)(0).isTraversable()) {
      return Direction.RIGHT;
    } else if (neighbors(Direction.LEFT).size > 0 && pacmanRow < monsterRow && neighbors(Direction.LEFT)(0).isTraversable()) {
      return Direction.LEFT;
    } else {
      return null;
    }
  }

  def getRandomDirection(): Direction = {
    var i = 10;
    do {
      var randomInt = random.nextInt(directions.size);
      var randomDirection = directions(randomInt);
      if (neighbors(randomDirection)(0) != null && neighbors(randomDirection)(0).isTraversable()
        && randomDirection != Direction.getOppositeDirection(aiControlledCreatureDirection)) {
        return randomDirection;
      }
      if (i < 0) {
        return randomDirection;
      }
      i -= 1;
    } while (true);
    return null
  }
}
