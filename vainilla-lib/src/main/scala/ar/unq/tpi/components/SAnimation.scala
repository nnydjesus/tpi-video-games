package ar.unq.tpi.components
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.appearances.Animation
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

class SAnimation(meantime: Double, sprites: Array[Sprite]) extends Animation(meantime, sprites) with EventGameComponent[SAnimation] {

  override def advance() = {
    if (this.getCurrentIndex() + 1 >= this.getSprites().length) {
      dispatchEvent(new Event(GenericGameEvents.FINISH_ANIMATION, this))
    }
    super.advance()
  }

}