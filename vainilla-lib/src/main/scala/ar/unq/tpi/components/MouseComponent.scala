package ar.unq.tpi.components
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key

class MouseComponent(sprite: Sprite, x: Double, y: Double) extends SpriteComponent(sprite, x, y) {
  
//  this.addKeyPressetListener(Key.LEFT, (delta)=> this.move(-100, 0))
//  this.addKeyPressetListener(Key.RIGHT, (delta)=> this.move(100, 0))
//  this.addKeyPressetListener(Key.UP, (delta)=> this.move(0, 100))
//  this.addKeyPressetListener(Key.DOWN, (delta)=> this.move(0, -100))


  setZ(100)
  override def update(deltaState: DeltaState) {
    super.update(deltaState)
    this.setX(deltaState.getCurrentMousePosition().getX())
    this.setY(deltaState.getCurrentMousePosition().getY())
  }

}