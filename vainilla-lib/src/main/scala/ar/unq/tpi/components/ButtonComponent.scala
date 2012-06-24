package ar.unq.tpi.components
import java.awt.Graphics2D
import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.traits.EventGameScene
import ar.edu.unq.tpi.traits.FunctionSceneListener

class ButtonComponent[SceneType <: EventGameScene](sprite: Sprite, over: Sprite, x: Double, y: Double, listener:(DeltaState)=>Unit) 
	extends SpriteComponent[SceneType](sprite, x, y) {
  
  override def setScene(scene:SceneType){
    scene.addMousePressetListener(this, MouseButton.LEFT, new FunctionSceneListener(onClick))
  }
  
  override def update(deltaState: DeltaState) {
    if (isClickMe(deltaState.getCurrentMousePosition())) {
      this.setAppearance(over)
    }else{
      this.setAppearance(sprite)
    }
  }

  def onClick(deltaState: DeltaState) {
    if (isClickMe(deltaState.getCurrentMousePosition())) {
    	click(deltaState)
    }

  }
  def click(deltaState: DeltaState) = listener(deltaState) 

}



class ButtonTextComponent[SceneType <: EventGameScene](sprite: Sprite, over: Sprite, var label: Label, x: Double, y: Double, listener:(DeltaState)=>Unit) 
				extends ButtonComponent[SceneType](sprite, over, x, y, listener) {

  override def update(deltaState: DeltaState) {
    super.update(deltaState)
    label.update(deltaState.getDelta())

  }

  override def render(graphics: Graphics2D) {
    super.render(graphics)
    var currentX = this.getX()
    var currentY = this.getY()
    this.setX(this.getX() + (this.getAppearance().getWidth()/2) - label.getWidth()/2)
    this.setY(this.getY() + (this.getAppearance().getHeight()/2) - label.getHeight()/1.5)
    label.render(this, graphics)
    
    this.setX(currentX)
    this.setY(currentY)
  }

}