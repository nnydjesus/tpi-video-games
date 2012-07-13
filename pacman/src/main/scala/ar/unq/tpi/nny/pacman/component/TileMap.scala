package ar.unq.tpi.nny.pacman.component
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.nny.pacman.domain.Position
import ar.unq.tpi.components.SpriteComponent
import java.awt.Graphics2D
import ar.unq.tpi.nny.pacman.util.GameConstants
import scala.collection.mutable.Map
import com.uqbar.vainilla.DeltaState
import ar.unq.tpi.nny.pacman.scene.PlayScene
import java.awt.image.BufferedImage
import ar.unq.tpi.nny.pacman.domain.Matrix
import java.awt.AlphaComposite
import java.awt.geom.Rectangle2D
import java.awt.Color
import ar.unq.tpi.nny.pacman.domain.Block
import ar.unq.tpi.nny.pacman.util.GameImages._
import ar.unq.tpi.nny.pacman.domain.Wall
import ar.unq.tpi.nny.pacman.domain.SimpleWall

class TileMap(var blocks: Matrix[Block], x: Double, y: Double) extends SpriteComponent[PlayScene](new Sprite(new BufferedImage(28 * 20, 36 * 20, BufferedImage.TYPE_INT_ARGB)), x, y) {
  setZ(-2)
  
  var mapDoubleBorder = Map[Int, Sprite](20 -> BORDER_Double2, 12 -> BORDER_Double1, 10 -> BORDER_Double3,  6 -> BORDER_Double1.flipVertically(), 18 -> 
  BORDER_Double1.flipHorizontally().flipVertically(), 24->BORDER_Double1.flipHorizontally(),  4->BORDER_Double2, 16->BORDER_Double2)
  
  var mapSimpleBorder = Map[Int, Sprite](20 -> BORDER_Simple2, 12 -> BORDER_Simple3.flipHorizontally(), 10 -> BORDER_Simple3,  6 -> BORDER_Simple1.flipVertically(), 18 -> 
  BORDER_Simple1.flipHorizontally().flipVertically(), 24->BORDER_Simple3,  4->BORDER_Simple2, 16->BORDER_Simple2, 
  0->BORDER_Simple4, 2->BORDER_Simple4, 22->BORDER_Simple2, 28->BORDER_Simple2.flipVertically(), 26->BORDER_Simple3, 30->BORDER_Simple4, 14->BORDER_Simple3.flipHorizontally())
  
  var sprite: Sprite = _
  var doRender = false

  var matrix2 = new Matrix[Sprite]()

  override def setScene(scene: PlayScene) {
    super.setScene(scene)
    blocks.elems.keys.foreach(i => {
      blocks(i).keys.foreach(j => {
        var block = blocks(i, j)
        if (block != null) {

          block match {
            case wall: Wall => {
              var total = 0
              total += pointForblockWall(i, j - 1, 2)
              total += pointForblockWall(i + 1, j, 4)
              total += pointForblockWall(i, j + 1, 8)
              total += pointForblockWall(i - 1, j, 16)
              matrix2.put(i, j, get(mapDoubleBorder, total))
            }
            case wall: SimpleWall=> {
              var total = 0
              total += pointForblockSWall(i, j - 1, 2)
              total += pointForblockSWall(i + 1, j, 4)
              total += pointForblockSWall(i, j + 1, 8)
              total += pointForblockSWall(i - 1, j, 16)
              matrix2.put(i, j, get(mapSimpleBorder, total))
            }
            case _ => 
          }
        }

      })
    })
  }
  
  def get(map:Map[Int,Sprite], key:Integer):Sprite ={
    if(map.contains(key)){
      return map(key)
    }else{
      return BORDER_Simple4
    }
  }

  def pointForblockWall(i: Int, j: Int, number: Int): Int = {
    if (blocks.elems.contains(i) && blocks(i).contains(j)) {
      blocks(i, j) match {
        case w:Wall=> return number;
        case _ => return 0;
      }
    } else {
      return 0
    }
  }
  
  def pointForblockSWall(i: Int, j: Int, number: Int): Int = {
    if (blocks.elems.contains(i) && blocks(i).contains(j)) {
      blocks(i, j) match {
        case w:SimpleWall=> return number;
        case _ => return 0;
      }
    } else {
      return 0
    }
  }

  override def update(state: DeltaState) {
  }

  override def render(graphics: Graphics2D) {
    if (sprite == null) {
      var graphics = getAppearance().getImage().createGraphics()
      matrix2.elems.keys.foreach(i => {
        matrix2(i).keys.foreach(j => {
          sprite = matrix2(i, j)
          sprite.doRenderAt(i * GameConstants.TILE, j * GameConstants.TILE, graphics)
        })
      })
      graphics.dispose();
    }
    super.render(graphics)
  }

}