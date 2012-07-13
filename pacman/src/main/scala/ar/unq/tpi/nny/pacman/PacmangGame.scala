package ar.unq.tpi.nny.pacman
import java.awt.Dimension
import java.awt.Toolkit
import ar.edu.unq.tpi.ranking.Player
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.FunctionEvent
import ar.edu.unq.tpi.ranking.Ranking
import ar.edu.unq.tpi.ranking.PlayersRanking
import javax.swing.JOptionPane
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.DesktopGameLauncher
import ar.unq.tpi.nny.pacman.scene.RankingGameScene
import ar.unq.tpi.nny.pacman.scene.InitGameScene
import ar.unq.tpi.nny.pacman.scene.PlayScene
import util.GameEvents

class PacmangGame extends Game {

  var rankingScene: RankingGameScene = null
  var initScene: InitGameScene = null

  override def initializeResources() {
  }

  override def setUpScenes() {
    var initScene = new InitGameScene()

    initScene.addEventListener(GameEvents.START, playGame)
    this.setCurrentScene(initScene)
  }

  def playGame: Event[InitGameScene, Any] => Unit = event => {
    this.setCurrentScene(new PlayScene())
  }

  def getDisplaySize(): Dimension = new Dimension(850, 750)

  override def getTitle() = "Demo"

}

object PacmangGame {

  def main(args: Array[String]) = {
    (new DesktopGameLauncher(new PacmangGame())).launch()
  }

}