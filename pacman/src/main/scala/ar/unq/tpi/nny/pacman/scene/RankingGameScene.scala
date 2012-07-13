package ar.unq.tpi.nny.pacman.scene
import ar.edu.unq.tpi.traits.EventGameScene
import ar.unq.tpi.components.LabelComponent
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.ranking.Ranking
import ar.edu.unq.tpi.resource.TraitResources
import com.uqbar.vainilla.appearances.Label
import ar.unq.tpi.components.MouseComponent
import ar.unq.tpi.components.ButtonTextComponent
import com.uqbar.vainilla.GameComponent
import java.awt.Color
import ar.unq.tpi.components.Stats
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import ar.unq.tpi.components.BackroundComponent
import ar.unq.tpi.nny.pacman.util.GameImages
import ar.unq.tpi.nny.pacman.util.Fonts
import ar.unq.tpi.nny.pacman.util.GameEvents

class RankingGameScene() extends GameScene with TraitResources with EventGameScene with EventGameComponent[RankingGameScene]{
  
//  val RANKING = Ranking.getRanking()
//  val TITLE = new LabelComponent("Asteroids-- Ranking de Jugadores", Fonts.GODOFWAR.deriveFont(80f), Color.WHITE, 0, 0)
//  val BACK_SPRITE = GameImages.START_GAME
//  val BACK_SPRITE_OVER = GameImages.START_GAME_OVER
//
//  override def setGame(game: Game) {
//    super.setGame(game)
//
//    this.addComponent(new Stats(game.getDisplayWidth() - 200, 0))
//    TITLE.centerX(game.getDisplayWidth())
//    this.addComponent(TITLE)
//
//    var pos = 1
//    RANKING.ranking.foreach(player => {
//      this.addComponent(new LabelComponent((pos + "--" + player.name + "----" + player.score.toInt).toUpperCase(), Fonts.GODOFWAR.deriveFont(30f), Color.WHITE, 10, 100 + 50 * pos))
//      pos += 1
//    })
//
//    this.addComponent(new BackroundComponent(GameImages.SPACE, getGame().getDisplayWidth(), getGame().getDisplayHeight(), 0, 0))
//    var backLabel = new Label(Fonts.GODOFWAR.deriveFont(45f), Color.WHITE, "Back")
//    this.addComponent(new ButtonTextComponent(BACK_SPRITE, BACK_SPRITE_OVER, backLabel, game.getDisplayWidth() - BACK_SPRITE.getWidth(), game.getDisplayHeight() - BACK_SPRITE.getHeight() -50,
//      (deltaState) => {
//        dispatchEvent(new Event(GameEvents.INIT, this, null))
//      }))
//
//    this.addComponent(new MouseComponent(GameImages.MOUSE, 0, 0))

//  }

}