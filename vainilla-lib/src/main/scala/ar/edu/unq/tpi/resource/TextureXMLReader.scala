package ar.edu.unq.tpi.resource
import scala.collection.mutable.Buffer
import scala.io.Source
import scala.xml.Source._
import scala.xml.pull.XMLEventReader
import scala.xml.Node
import com.uqbar.vainilla.appearances.Sprite
import javax.imageio.ImageIO
import java.net.URL
import java.awt.image.BufferedImage

trait TextureXMLReader {

  def buildSprites(xml: Node, key: String, sprite: Sprite, flip: Boolean, scale: Double): Buffer[Sprite] = {
    var buffer = Buffer[Sprite]()
    var crop: Sprite = null

    (xml \ "sprite").foreach(n => {
      if ((n \ "@n").toString().contains(key)) {
        crop = sprite.crop((n \ "@x").toString().toInt, (n \ "@y").toString().toInt, (n \ "@w").toString().toInt, (n \ "@h").toString().toInt)
        if (scale != 0 && scale != 1) {
          crop = crop.scale(scale, scale)
        }
        if (flip) {
          crop = crop.flipHorizontally()
        }
        buffer.append(crop)
      }
    })

    return buffer

  }

}
