package ar.edu.unq.tpi
import ar.unq.tpi.components.CenterComponent
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.DeltaState
import traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

class RoundComponent(appearance:Appearance, width:Double, height:Double, meantime:Double) extends CenterComponent(appearance, width, height) with EventGameComponent[RoundComponent]{
  
  var remainingTime = meantime
  
  override def update(state:DeltaState){
    super.update(state)
    remainingTime -= state.getDelta();
    
    if(remainingTime <= 0){
      dispatchEvent(new Event(GameEvents.FINISH_ANIMATION, this))      
    }
  }

}