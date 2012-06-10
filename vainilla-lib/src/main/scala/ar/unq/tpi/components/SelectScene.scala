package ar.unq.tpi.components
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.EventGameScene

abstract class SelectScene() extends GameScene() with EventGameScene with TraitResources{
  def selectItem(arena: Selectable) {}
}