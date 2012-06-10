package ar.edu.unq.tpi
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.GameScene
import java.awt.Graphics2D

class LifeBar(barSprite:Sprite, backgroundbar:Sprite, x:Double, y:Double, lifeFunction:()=>Double) extends GameComponent[GameScene, Sprite](barSprite.copy(), x, y) {
  
  override def update(deltaState: DeltaState) = {
    var life = lifeFunction()
    if(life >0){
    	this.setAppearance(barSprite.crop(0,0, ((barSprite.getWidth()*life)/100).toInt, barSprite.getHeight().toInt))
    }else{
      this.setAppearance(barSprite.crop(0,0, 1, barSprite.getHeight().toInt))
    }
  }
  
  override def render(graphics:Graphics2D){
    super.render(graphics)
    backgroundbar.render(this, graphics)
  }
}
