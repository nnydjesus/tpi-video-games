package ar.unq.tpi.components
import java.awt.Font
import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import java.awt.Color
import com.uqbar.vainilla.DeltaState

class Stats(font:Font, color:Color, x:Double, y:Double) extends LabelComponent[GameScene]("",  font, color, x, y) {
  
  def this(x:Double, y:Double) = {
    this(new Font(Font.SANS_SERIF, Font.BOLD, 18), Color.BLUE, x, y)
  }
  
  override def update(deltaState:DeltaState) ={
    super.update(deltaState)
    this.getAppearance().setText(this.text(deltaState))
  }
  
  def text(deltaState: DeltaState):String = {
    "FPS: " + (1/deltaState.getDelta()).toInt + "\nComponents: " + this.getGame().getCurrentScene().getComponentCount()
  }

}