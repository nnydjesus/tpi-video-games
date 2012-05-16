package ar.edu.unq.tpi

import java.awt.Dimension
import java.awt.Toolkit

import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.Event
import ar.unq.tpi.components.Selectable

class Fight extends Game with TraitResources {

	var state : StateRaund = FirstRaund		  

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
    var loading = new LoadingScene(this)
    setCurrentScene(loading)
    var gameScene: GamePlayScene = new GamePlayScene(this, character, arena)
    setCurrentScene(gameScene)
    gameScene.addEventListener(GameEvents.FINISH_FIGTH, finishFigth)
  }
  
  def finishFigth(event:Event[GamePlayScene, Nothing]){
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

