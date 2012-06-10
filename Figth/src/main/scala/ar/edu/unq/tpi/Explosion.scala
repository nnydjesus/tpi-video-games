package ar.edu.unq.tpi

import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.FunctionEvent
import ar.unq.tpi.components.GenericGameEvents
import ar.unq.tpi.components.SAnimation
import ar.unq.tpi.components.AnimationComponent


class Explosion[SceneType <: GameScene](animation: SAnimation, x: Double, y: Double, any: Any) extends AnimationComponent[SceneType, Explosion[SceneType]](animation, x, y) {

  def this(animation: SAnimation, x: Double, y: Double) = {
    this(animation, x, y, null)

    animation.addEventListener(GenericGameEvents.FINISH_ANIMATION, new FunctionEvent(onFinish))
  }
  
  def onFinish(e:Event[SAnimation, Any]) {
      destroy()
      dispatchEvent(new Event(GameEvents.FINISH_ANIMATION, this))
    }
}