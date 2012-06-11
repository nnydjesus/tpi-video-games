package ar.unq.tpi.components
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState

class SelectComponent(scene: SelectScene, var background:Selectable, x: Int, y: Int, width:Int, height:Int) extends SpriteComponent(background.image, x, y) {
  
  override def update(deltaState:DeltaState){
    super.update(deltaState)
    if(deltaState.isMouseButtonPressed(MouseButton.LEFT)){
      if(isClickMe(deltaState.getCurrentMousePosition())){
    	  scene.selectItem(background)
      }
    }
  }
  
}