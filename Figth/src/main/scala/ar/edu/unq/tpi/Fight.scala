package ar.edu.unq.tpi

import java.awt.Dimension
import java.awt.Toolkit

import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.{Event, FunctionEvent}
import ar.unq.tpi.components.Selectable

class Fight extends Game with TraitResources {

  protected def initializeResources(): Unit = {
  }

  protected def setUpScenes(): Unit = {
    setCurrentScene(new SelectCharacterScene(this))
//        playGame(Ragna, Arena1)
  }

  def selectArena(character: SelectableCharacter) {
    setCurrentScene(new SelectArenaScene(this, character.character))
  }

  def playGame(character: CharacterAppearance, arena: Selectable) {
    setCurrentScene(new LoadingScene(character, Litchi))
    var gameScene: GamePlayScene = new GamePlayScene(this, character, Litchi,  arena)
    setCurrentScene(gameScene)
    gameScene.addEventListener(GameEvents.FINISH_FIGTH, new FunctionEvent(finishFigth))
    gameScene.startRound(FirstRound)
  }
  
  def finishFigth(event:Event[GamePlayScene, Any]){
    setUpScenes()
  }


  def getDisplaySize(): Dimension = Toolkit.getDefaultToolkit().getScreenSize()
  
  def getTitle(): String = "Demo"

}

object MainFight {

  def main(args: Array[String]) {
    new DesktopGameLauncher(new Fight()).launch();
  }
}


