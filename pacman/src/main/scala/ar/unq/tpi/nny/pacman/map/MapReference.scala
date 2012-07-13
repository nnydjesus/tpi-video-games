package ar.unq.tpi.nny.pacman.map
import com.uqbar.vainilla.GameComponent
import ar.unq.tpi.nny.pacman.util.GameConstants
import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import ar.unq.tpi.collide.PixelPerfectCollition

class MapReference {
  var width = 960 //TODO hardcodeado
  var height = 690 //TODO hardcodeado
  var components = Map[Double, Map[Double, PixelPerfectCollition[_, _]]]()

  def +(component: PixelPerfectCollition[_, _]) {
    var abX = Math.ceil(component.getX() / GameConstants.TILE)
    var abY = Math.ceil(component.getY() / GameConstants.TILE)

    if (!components.contains(abX)) {
      components(abX) = Map()
    }
    components(abX)(abY) = component
  }

  def getElementFromPosition(component: PixelPerfectCollition[_, _]): Buffer[PixelPerfectCollition[_, _]] = {
    var buffer = Buffer[PixelPerfectCollition[_, _]]()
    var abX = Math.ceil(component.getX() / GameConstants.TILE)
    var abY = Math.ceil(component.getY() / GameConstants.TILE)

    if (components.contains(abX)) {
      if (components(abX).contains(abY)) {
        buffer.append(components(abX)(abY))
      }
      if (components(abX).contains(abY + 1)) {
        buffer.append(components(abX)(abY + 1))
      }
      if (components(abX).contains(abY - 1)) {
        buffer.append(components(abX)(abY - 1))
      }
    }

    if (components.contains(abX - 1)) {
      if (components(abX - 1).contains(abY)) {
        buffer.append(components(abX - 1)(abY))
      }
      if (components(abX + 1).contains(abY)) {
        buffer.append(components(abX + 1)(abY))
      }
    }

    return buffer;
  }

  def inBounds(x: Int, y: Int) = !(x < 0 || y < 0 || x >= width || y >= height)

  /**
   * Move attempt method. Changes the position the map of the game object if there are no obstructions
   *
   * @param act The actor object trying to move
   * @param x A x coordinate to move to
   * @param y A y coordinate to move to
   * @return True if the move succeeded. False if otherwise
   */
  def canMove(component: GameComponent[_, _], x: Int, y: Int): Boolean = {
    // Check bounds
//    if (!inBounds(x, y))
//      return false;

    var abX = Math.ceil(x / GameConstants.TILE)
    var abY = Math.ceil(y / GameConstants.TILE)
    
    if(components.contains(abX) && components(abX).contains(abY)){
      return false
    }
    return true;
  }
  
  def getCost(component: GameComponent[_, _], sx: Int, sy: Int, tx: Int, ty: Int) = 1F
  
}