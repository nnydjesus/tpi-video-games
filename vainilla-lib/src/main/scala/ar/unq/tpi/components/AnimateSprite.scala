package ar.unq.tpi.components
import java.awt.image.BufferedImage
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.appearances.Sprite
import java.awt.geom.AffineTransform

class AnimateSprite(var originalImage: BufferedImage) extends Sprite(originalImage) {

  var scale = 0.5

  override def update(delta: Double) {
    if(scale <1){
    	this.setImage(originalImage)
    	this.setImage(this.getTransformedImage(AffineTransform.getScaleInstance(scale , scale )))
    	scale = scale +0.1
    }
    
  }
  
}

trait CenterComponent[SceneType <:GameScene, A <:Appearance] extends GameComponent[SceneType , A]{
  
  val width:Double
  val height:Double
  
  override def update(deltaState: DeltaState) = {
      super.update(deltaState)
      this.setX((width/2) - (this.getAppearance().getWidth() / 2))
      this.setY((height/2) - (this.getAppearance().getHeight() / 2))
    }
}