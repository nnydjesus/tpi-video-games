package ar.edu.unq.tpi
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.EventGameScene
import ar.unq.tpi.components.BackroundComponent
import ar.unq.tpi.components.ButtonTextComponent
import com.uqbar.vainilla.appearances.Label
import java.awt.Color
import com.uqbar.vainilla.DeltaState
import scala.collection.mutable.LinkedList
import scala.collection.mutable.DoubleLinkedList
import scala.collection.JavaConversions._
import com.uqbar.vainilla.events.constants.Key
import ar.edu.unq.tpi.traits.FunctionSceneListener
import com.uqbar.vainilla.appearances.Sprite
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

class InitScene extends GameScene() with EventGameScene with TraitResources with EventGameComponent[InitScene]{
  
  var buttonList:DoubleLinkedList[KeyboardTextComponent]=_
  
  override def setGame(game:Game){
    super.setGame(game)
    val background = new BackroundComponent(GameImage.LOGO, game.getDisplayWidth(), game.getDisplayHeight(), 0, 0)
    this.addComponent(background)
    this.addKeyPressetListener(background, Key.DOWN, new FunctionSceneListener(next))
    this.addKeyPressetListener(background, Key.UP, new FunctionSceneListener(prev))
    this.addKeyPressetListener(background, Key.ENTER, new FunctionSceneListener(enter))
    this.buttonList = DoubleLinkedList(new KeyboardTextComponent(GameImage.INIT_BUTTON, GameImage.INIT_BUTTON_OVER, new Label(Fonts.GODOFWAR.deriveFont(25f), Color.WHITE, "1 Player"), 500,500, onePlayer, true),
    new KeyboardTextComponent(GameImage.INIT_BUTTON, GameImage.INIT_BUTTON_OVER, new Label(Fonts.GODOFWAR.deriveFont(25f), Color.WHITE, "2 Player"), 500,550, twoPlayer),
    new KeyboardTextComponent(GameImage.INIT_BUTTON, GameImage.INIT_BUTTON_OVER, new Label(Fonts.GODOFWAR.deriveFont(25f), Color.WHITE, "option"), 500,600, options))
    
    this.addComponents(buttonList)
  }
  
  
  def onePlayer(state:DeltaState){
    dispatchEvent(new Event(GameEvents.SELECT_PLAYER, this, PlayerCPU))
  }
  
  def twoPlayer(state:DeltaState){
    dispatchEvent(new Event(GameEvents.SELECT_PLAYER, this, Player2))
  }
  
  def options(state:DeltaState){
    dispatchEvent(new Event(GameEvents.OPTIONS, this))
  }
  
  def next(state:DeltaState){
    if(buttonList.next.elem != null){
	    buttonList.elem.unSelected
	    buttonList = buttonList.next
	    buttonList.elem.inSelected
    }
  }
  
  def prev(state:DeltaState){
    if(buttonList.prev != null && !buttonList.prev.isEmpty){
	    buttonList.elem.unSelected
	    buttonList = buttonList.prev
	    buttonList.elem.inSelected
    }
  }
  
  def enter(state:DeltaState){
	  buttonList.elem.click(state)
  }

}

class KeyboardTextComponent(sprite: Sprite, over: Sprite, label: Label, x: Double, y: Double, listener:(DeltaState)=>Unit, var selected:Boolean=false) extends ButtonTextComponent[InitScene](sprite, over, label, x, y, listener){
  
  
  override def update(state:DeltaState){
    super.update(state)
    if(selected){
      setAppearance(over)
    }else{
      setAppearance(sprite)
    }
  }
  
  def inSelected = this.selected = true
  def unSelected = this.selected = false
} 
				



