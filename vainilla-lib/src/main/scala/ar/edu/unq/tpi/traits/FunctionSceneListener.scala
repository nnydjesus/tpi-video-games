package ar.edu.unq.tpi.traits
import com.uqbar.vainilla.DeltaState

class FunctionSceneListener(f:(DeltaState) => Unit) {
  
  def apply(state:DeltaState) = f(state)

}