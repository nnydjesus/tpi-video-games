package ar.unq.tpi.nny.pacman.map
import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.nny.pacman.scene.PlayScene
import scala.collection.IndexedSeqOptimized
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.Graphics
import javax.imageio.ImageIO
import java.io.File
import java.awt.Color
import ar.unq.tpi.nny.pacman.util.GameConstants

class MapGenerator(scene: PlayScene) extends TraitResources {

  def this() {
    this(null)
  }

  def generateByText(fileName: String) {
    doProcess(fileName, processCode)
  }

  def generateByImage(fileName: String) {
    var image = getImage(fileName)
    var index = 0
    for (x <- 0 until image.getWidth() / GameConstants.SIZE_PIXEL_MAP_DENSITY) {
      for (y <- 0 until image.getHeight() / GameConstants.SIZE_PIXEL_MAP_DENSITY) {
        var rgb = image.getRGB(x * GameConstants.SIZE_PIXEL_MAP_DENSITY, y * GameConstants.SIZE_PIXEL_MAP_DENSITY)
        processColorImage(new Color(rgb), x, y)
      }
    }
  }

  def createMapDensity(fileName: String) {
    var image = new BufferedImage(28 * GameConstants.SIZE_PIXEL_MAP_DENSITY, 31 * GameConstants.SIZE_PIXEL_MAP_DENSITY, BufferedImage.TYPE_INT_ARGB)
    var graphics = image.createGraphics()
    doProcess(fileName, processImage(graphics, GameConstants.SIZE_PIXEL_MAP_DENSITY))
    graphics.dispose();
    ImageIO.write(image, GameConstants.MAP_DENSITY_TYPE, new File(PATH + "img/" + GameConstants.MAP_DENSITY_NAME));
  }

  protected def doProcess(fileName: String, process: (String, Int, Int) => Unit) {
    var fileMap = file(fileName)
    var index = 0
    fileMap.getLines().foreach(line => {
      processLine(line, index, process)
      index += 1
    })
  }

  protected def processColorImage(color: Color, x: Int, y: Int) {
    var token = Token.valueByColor(color)
    token.action(scene, x, y)
  }

  protected def processLine(line: String, y: Int, process: (String, Int, Int) => Unit) {
    var x = 0
    var lineToProcess = line.split(",")
    lineToProcess.foreach(code => {
      process(code.toString(), x, y)
      x += 1
    })
  }

  protected def processCode(code: String, x: Int, y: Int) {
    var token = Token.value(code)
    token.action(scene, x, y)
  }

  protected def processImage(image: Graphics, size: Int)(code: String, x: Int, y: Int) {
    var token = Token.value(code)
    token.draw(x, y, image, size)
  }

}

object MapDensityGenerator {
  def main(args: Array[String]) {
    new MapGenerator().createMapDensity(GameConstants.MAP_TEXT_NAME)
  }
}