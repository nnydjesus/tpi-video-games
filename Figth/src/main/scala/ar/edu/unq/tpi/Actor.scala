package ar.edu.unq.tpi
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.GameScene
import ar.unq.tpi.components.AnimationComponent
import ar.edu.unq.tpi.traits.EventGameComponent

abstract class Actor[SceneType <:GameScene, T<:EventGameComponent[T]](var defaultAnimation: Animation, x: Int, y: Int) extends AnimationComponent[SceneType, T](defaultAnimation, x, y){
  
  var isMoving = false;
  
  def changeMove(move:Movement)// = this.setAppearance(animations(state))
  
}