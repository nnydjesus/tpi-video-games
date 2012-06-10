package ar.edu.unq.tpi.resource
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import scala.collection.mutable.Buffer
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.sound.SoundBuilder
import javax.imageio.stream.FileImageInputStream
import javax.imageio.ImageIO
import java.io.FileInputStream
import javazoom.jl.player.Player
import java.io.FileOutputStream
import java.awt.Font
import scala.io.Source

trait TraitResources {

  val PATH = "src/main/resources/"

  def sprite(name: String): Sprite = Sprite.fromImage("/img/" + name)

  def spriteS(sWidth: Double, sHeight: Double, row: Int, cols: Int, image: BufferedImage, scaleX: Double, scaleY: Double, initRows:Int=0, initCols:Int=0): Array[Sprite] = {
    val buffer = Buffer[Sprite]()
    for (i <- initCols to (cols+initCols) - 1 by 1) {
      for (j <- initRows to (row+initRows) - 1 by 1) {
    	  buffer.append(new Sprite(image.getSubimage(sWidth.toInt * j, i * sHeight.toInt, sWidth.toInt, sHeight.toInt)).scale(scaleX, scaleY))
      }
    }
    return buffer.toArray
  }

  def getImage(name: String) = ImageIO.read(this.getClass().getResource("/img/" + name))
  def getFileOutputStream(name: String) = new FileOutputStream(PATH + name)
  def getFileInputStream(name: String) = new FileInputStream(PATH + name)

  def xmlFromFile(path: String) = scala.xml.XML.load(scala.xml.Source.fromFile(PATH + path))
  def file(path: String) =  Source.fromFile(PATH + path)

  def getSound(path: String) = new SoundBuilder().buildSound("/sound/" + path)
  def getResource(path: String) = this.getClass().getResourceAsStream(path)
  def getResourceURL(path: String) = this.getClass().getResource(path)
  def getFont(path: String) = Font.createFont(Font.TRUETYPE_FONT, getResource("/fonts/" + path))
  def fileSound(path: String) = getResource("/sound/" + path)
  def getPlayer(path: String) = new Player(fileSound(path))

}

object SpriteUtil extends TraitResources() {

}

