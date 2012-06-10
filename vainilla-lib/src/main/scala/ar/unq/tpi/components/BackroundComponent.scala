package ar.unq.tpi.components
import java.awt.image.BufferedImage
import java.awt.Graphics2D

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameScene

import ar.edu.unq.tpi.traits.EventGameScene

class BackroundComponent[SceneType <: GameScene](sprite: Sprite, width: Double, heigth: Double, x: Double, y: Double) extends SpriteComponent[SceneType](sprite.scale(width / sprite.getWidth(), heigth / sprite.getHeight()), x, y) {
  this.setZ(-10)
}

class ScrollingBackroundComponent[SceneType <: GameScene](var sprite: ScrollingSprite, x: Double, y: Double) extends SpriteComponent[SceneType](sprite, x, y) {
}

class ScrollingSprite(image: BufferedImage, width: Int, step:Int, a: Any) extends Sprite(image) {

  var currentPointer: Int = 0

  def this(image: BufferedImage, width: Int, step:Int) {
    this(image, width, step, null)
    currentPointer = (getImage().getWidth() - width) / 2
  }

  override def doRenderAt(x: Int, y: Int, graphics: Graphics2D) {
    graphics.drawImage(this.getImage(), x - currentPointer, y, null);
  }

  def avance():Boolean = {
    currentPointer += step
    if (currentPointer + width > getImage().getWidth()) {
      currentPointer = getImage().getWidth() - width
      return false
    }
    return true
  }

  def retroceder():Boolean = {
    currentPointer -= step
    if (currentPointer < 0) {
      currentPointer = 0
      return false
    }
    return true
  }
}
