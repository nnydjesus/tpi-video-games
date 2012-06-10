package ar.edu.unq.tpi
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameScene
import java.awt.Graphics2D

class LifeBar(barSprite: Sprite, backgroundbar: Sprite, bar1: Sprite, bar2: Sprite, selectedImage: Sprite, invertir: Boolean, x: Double, y: Double, lifeFunction: () => Double) extends GameComponent[GameScene, Sprite](barSprite.copy(), x, y) {
  
  var oldlife = -1D
  var deltaLife = 0D

  override def update(deltaState: DeltaState) = {
    var life = lifeFunction()
    if(oldlife == -1D){
      oldlife = life
    }
    if(oldlife != life){
    	deltaLife = oldlife - life
    }
    
    if(life == 100){
      deltaLife = 0
    }
    
    if (life > 0) {
      if (invertir) {
        var move = ((barSprite.getWidth() * (life+deltaLife)) / 100).toInt
        this.setAppearance(barSprite.crop(barSprite.getWidth().toInt - move, 0, move, barSprite.getHeight().toInt))
      } else {
        this.setAppearance(barSprite.crop(0, 0, ((barSprite.getWidth() * (life+deltaLife)) / 100).toInt, barSprite.getHeight().toInt))
      }
    } else {
      if (invertir) {
        this.setAppearance(barSprite.crop(barSprite.getWidth().toInt-1, 0, 1, barSprite.getHeight().toInt))
      } else {
        this.setAppearance(barSprite.crop(0, 0, 1, barSprite.getHeight().toInt))

      }
    }
    
    deltaLife -= (deltaState.getDelta()*30)
    if(deltaLife <0){
      deltaLife = 0
    }
    oldlife = life
    
    if(deltaLife >0){
      println("deltaL: " + deltaLife)
    }
  }

  override def render(graphics: Graphics2D) {
    bar1.render(this, graphics)
    if(invertir){
      selectedImage.renderAt(this.getX().toInt + (selectedImage.getWidth().toInt/2), this.getY().toInt, graphics)
    }else{
      selectedImage.renderAt(this.getX().toInt + bar1.getWidth().toInt - selectedImage.getWidth().toInt, this.getY().toInt, graphics)
    }
    bar2.render(this, graphics)
    backgroundbar.render(this, graphics)
    if(invertir){
      this.getAppearance().renderAt((this.getX()+barSprite.getWidth() - this.getAppearance().getWidth()).toInt, this.getY().toInt, graphics)
      
    }else{
    	super.render(graphics)
    }
  }
}
