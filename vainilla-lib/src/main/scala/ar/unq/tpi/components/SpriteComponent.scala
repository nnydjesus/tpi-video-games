package ar.unq.tpi.components
import java.awt.image.BufferedImage
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.collide.CollitionBounds
import ar.unq.tpi.collide.PixelPerfectCollition
import com.uqbar.vainilla.appearances.Appearance

class SpriteComponent[SceneType <:GameScene](sprite:Sprite, x:Double, y:Double) extends GameComponent[SceneType, Sprite](sprite, x, y) 
	with CollitionBounds[SceneType, Sprite] with PixelPerfectCollition[SceneType, Sprite] with TraitResources{
  
  def getImage(): BufferedImage = this.getAppearance().getImage()
  def width:Double = this.getAppearance().getWidth()
  def height:Double = this.getAppearance().getHeight()

}

object ScaleSpriteComponent{
 
  def scale(sprite:Sprite, width:Double, height:Double) = sprite.scale(width/sprite.getWidth(), height/sprite.getHeight())  
}

class SpriteCenterComponent[SceneType <:GameScene](sprite:Sprite,override val twidth:Double, override val theight:Double ) extends SpriteComponent[SceneType](sprite, 0,0) 
		with CenterComponent[SceneType, Sprite] {}


trait ComponentUtils[SceneType <:GameScene, A <: Appearance] extends GameComponent[SceneType, A]{
  
  def centerX(width:Double){
    this.setX(width/2 - this.getAppearance().getWidth()/2)
  }
  
  def centerY(heigth:Double){
    this.setY(heigth/2 - this.getAppearance().getHeight()/2)
  }

  def center(width:Double, heigth:Double){
    this.centerX(width)
    this.centerY(heigth)
  }
}


