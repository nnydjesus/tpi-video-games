package ar.edu.unq.tpi.traits
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.unq.tpi.components.CenterComponent
import ar.unq.tpi.components.GenericGameEvents

trait RoundComponent[SceneType <:GameScene, A <:Appearance]  extends GameComponent[SceneType, A] with EventGameComponent[RoundComponent[SceneType, A]]{
  
  val meantime:Double
  var remainingTime = meantime
  
  override def update(state:DeltaState){
    super.update(state)
    remainingTime -= state.getDelta();
    
    if(remainingTime <= 0){
      dispatchEvent(new Event(GenericGameEvents.FINISH_ANIMATION, this))      
    }
  }

}