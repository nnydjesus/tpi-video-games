package ar.unq.tpi.components
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.EventGameScene

class LabelComponent[SceneType <: GameScene](var text: String, font: Font, color: Color, x: Double, y: Double) extends GameComponent[SceneType, Label](
  new Label(font, color, text), x, y) with ComponentUtils[SceneType, Label] {

  override def update(deltaState: DeltaState) {
    super.update(deltaState)
    this.getAppearance().setText(text)
  }

}

class LabelImageComponent[SceneType <: EventGameScene](sprite: Sprite, var label: Label, x: Double, y: Double, text: (DeltaState) => String)
  extends SpriteComponent[SceneType](sprite, x, y) {

  override def update(state: DeltaState) {
    super.update(state)
    this.label.setText(text(state))

  }

  override def render(graphics: Graphics2D) {
    super.render(graphics)
    var currentX = this.getX()
    var currentY = this.getY()
    this.setX(this.getX() + (this.getAppearance().getWidth() / 2) - label.getWidth() / 2)
    this.setY(this.getY() + (this.getAppearance().getHeight() / 2) - label.getHeight() / 1.5)
    label.render(this, graphics)

    this.setX(currentX)
    this.setY(currentY)
  }

}


class AnimateLabelComponent[SceneType <: EventGameScene](text: String, font: Font, color: Color, x: Double, y: Double, minSize:Float, salto:Float, meantime:Double)
  extends LabelComponent[SceneType](text, font, color, x, y) with EventGameComponent[AnimateLabelComponent[SceneType]]{
  
  var currentMeantime = meantime
  var currentSize = minSize
  override def update(state: DeltaState) {
    super.update(state)
    center(getGame().getDisplayWidth(), getGame().getDisplayHeight())
    if(currentMeantime <= 0){
      dispatchEvent(new Event(GenericGameEvents.FINISH_ANIMATION, this, null))
      destroy()
    }else{
    	this.getAppearance().setFont(font.deriveFont(currentSize))
    	currentSize += (salto *state.getDelta()).toFloat
    }
    currentMeantime -= state.getDelta()
  }
  
}