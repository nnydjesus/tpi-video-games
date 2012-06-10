package ar.unq.tpi.collide
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

trait CollitionBounds[SceneType <:GameScene, A <: Appearance] extends GameComponent[SceneType, A] {
  
  def containLeft(bounds:GameComponent[GameScene, Bounds]):Boolean = {
    return (this.getX() >= bounds.getX())
  }
  def containRigth(bounds:GameComponent[GameScene, Bounds]):Boolean = {
    return ((this.getX() + this.getAppearance().getWidth()) <= bounds.getX() +bounds.getAppearance().getWidth())
  }
  
  def containTop(bounds:GameComponent[GameScene, Bounds]):Boolean = {
    return this.getY() >= bounds.getY()
  }
  
  def containBottom(bounds:GameComponent[GameScene, Bounds]):Boolean = {
    return ((this.getY() + this.getAppearance().getHeight()) <= bounds.getY() +bounds.getAppearance().getHeight())
  }
  
  def totalContain(bounds:GameComponent[GameScene, Bounds]):Boolean = {
    return this.containTop(bounds) && this.containLeft(bounds) && this.containRigth(bounds) && this.containBottom(bounds)
  }

}