package ar.edu.unq.tpi

import java.awt.Dimension
import java.awt.Toolkit

import scala.annotation.implicitNotFound

import com.uqbar.vainilla.DesktopGameLauncher
import com.uqbar.vainilla.Game

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.FunctionEvent
import ar.unq.tpi.components.Selectable

class Fight extends Game with TraitResources {
  var player1: Player = Player1
  var player2: Player = null

  protected def initializeResources(): Unit = {
  }

  protected def setUpScenes(): Unit = {
    //        setCurrentScene(new SelectCharacterScene(this))
    //    playGame(Ragna, Arena1)
    initScene()
  }

  def initScene() {
    var initScene = new InitScene()
    initScene.addEventListener(GameEvents.SELECT_PLAYER, selectPlayer)
    initScene.addEventListener(GameEvents.OPTIONS, options)
    setCurrentScene(initScene)
  }

  def selectPlayer: Event[InitScene, Player] => Unit = event => {
    player2 = event.data
    setCurrentScene(new SelectCharacterScene(this))
    //    playGame(Ragna, Arena1)
  }

  def options: Event[InitScene, Any] => Unit = event => {
    new ConfigureButtonsDialog(Player1)
  }

  def selectArena(character: SelectableCharacter) {
    setCurrentScene(new SelectArenaScene(this, character.character))
  }

  def playGame(character: CharacterAppearance, arena: Selectable) {
    var loading = new LoadingScene(character, Litchi)
    setCurrentScene(loading)

    loading.addEventListener(GameEvents.LOAD_RESOURCE, resourceLoaded(character, Litchi, arena))
  }

  def resourceLoaded(character1: CharacterAppearance, character2: CharacterAppearance, arena: Selectable): (Event[LoadingScene, Any]) => Unit =
    (event) => {

      var gameScene: GamePlayScene = new GamePlayScene(this, character1, character2, arena)
      setCurrentScene(gameScene)
      gameScene.addEventListener(GameEvents.FINISH_FIGTH, new FunctionEvent(finishFigth))
      gameScene.startRound(FirstRound)
    }

  def finishFigth(event: Event[GamePlayScene, Any]) {
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


