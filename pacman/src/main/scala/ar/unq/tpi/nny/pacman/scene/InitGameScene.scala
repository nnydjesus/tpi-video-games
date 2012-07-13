package ar.unq.tpi.nny.pacman.scene
import java.awt.Color
import java.awt.Font
import com.uqbar.vainilla.appearances.Label
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.EventGameScene
import ar.unq.tpi.components.BackroundComponent
import ar.unq.tpi.components.ButtonTextComponent
import ar.unq.tpi.components.ComponentUtils
import ar.unq.tpi.components.LabelComponent
import ar.unq.tpi.components.LabelImageComponent
import ar.unq.tpi.components.MouseComponent
import ar.unq.tpi.components.Stats
import ar.unq.tpi.nny.pacman.util.Fonts
import ar.unq.tpi.nny.pacman.util.GameImages
import ar.unq.tpi.nny.pacman.util.GameEvents
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.FunctionSceneListener
import java.awt.Graphics2D

class InitGameScene() extends GameScene with TraitResources with EventGameScene with EventGameComponent[InitGameScene] {

  override def setGame(game: Game) {
    super.setGame(game)

    var startGame = GameImages.LABEL

    this.addComponent(new BackroundComponent(GameImages.INIT, getGame().getDisplayWidth(), getGame().getDisplayHeight(), 0, 0))

    var startLabel = new Label(Fonts.CRACKMAN.deriveFont(30f), Color.WHITE, "")
    var startComponent = new LabelImageComponent(startGame, startLabel, (game.getDisplayWidth() / 2) - (startGame.getWidth() / 2),
      (game.getDisplayHeight() / 2) + 50 , (d) => "Start Game") {
      
      val meantime = 0.4
      var remainingTime = 0D
      var view = true

      InitGameScene.this.addKeyPressetListener(this, new FunctionSceneListener(onStart), Key.ENTER)
      
      override def update(state:DeltaState){
        super.update(state)
        if(remainingTime < 0 ){
          remainingTime = meantime
          view = !view
        }
        
        remainingTime -= state.getDelta()
        
      }
      
      override def render(graphics:Graphics2D){
        if(view){
        	super.render(graphics)
        }
      }

    }
    this.addComponent(startComponent)

  }

  def onStart(state: DeltaState) {
    dispatchEvent(new Event(GameEvents.START, this))
  }
}