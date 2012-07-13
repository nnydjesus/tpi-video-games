package ar.unq.tpi.nny.pacman.component
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.components.SpriteComponent
import ar.unq.tpi.nny.pacman.scene.PlayScene
import ar.unq.tpi.nny.pacman.util.GameConstants
import com.uqbar.vainilla.DeltaState
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.resource.SpriteUtil
import ar.unq.tpi.nny.pacman.util.GameEvents
import ar.unq.tpi.nny.pacman.domain.Item
import ar.unq.tpi.nny.pacman.util.GameImages
import ar.unq.tpi.nny.pacman.util.GameConstants
import ar.unq.tpi.components.ScaleSpriteComponent

abstract class ItemComponent(var item:Item, sprite:Sprite, x:Double, y:Double, soundName:String) extends 
		SpriteComponent[PlayScene]	(ScaleSpriteComponent.scale(sprite, GameConstants.TILE, GameConstants.TILE), x * GameConstants.TILE, y * GameConstants.TILE) 
		with EventGameComponent[ItemComponent]{
  
  lazy val sound = getSound(soundName)
  
  override def update(state:DeltaState){
    super.update(state)
    if(collidesWith(getScene().pacman)){
    	dispatchEvent(new Event(eventName, this))
    	destroy()
    	sound.play()
    }
  }
  
  protected def eventName:String
}

class PillComponent(item:Item, x:Double, y:Double) extends ItemComponent(item, GameImages.PILL, x:Double, y:Double, "Eat Dot.wav"){
  protected def eventName = GameEvents.COLLIDE_PACMAN_WITH_STAR
}

class PowerPillComponent(item:Item, x:Double, y:Double) extends ItemComponent(item, GameImages.POWER_PILL, x:Double, y:Double, "Eat Fruit.wav"){
  protected def eventName = GameEvents.COLLIDE_PACMAN_WITH_HEART
}

class CherryComponent(item:Item, x:Double, y:Double) extends ItemComponent(item, GameImages.CHERRY, x:Double, y:Double, "Eat Fruit.wav"){
  protected def eventName = GameEvents.REMOVE_CHERRY
}


