package ar.unq.tpi.components
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.appearances.Label
import java.awt.Font
import java.awt.Color
import ar.unq.tpi.components.LabelComponent
import ar.edu.unq.tpi.ranking.Player

class ScoreComponent(player: Player, x: Double, y: Double) extends LabelComponent[GameScene]("", new Font(Font.SANS_SERIF, Font.BOLD, 25), Color.WHITE, x, y) {

  override def update(delta: DeltaState) {
    super.update(delta)
    this.getAppearance().setText(this.text(delta))
    this.setZ(10)
  }
  
  def text(state:DeltaState) = player.name + "\nScore: " + player.score.toInt + "\nLife: " + player.life + "\nLevel: " + player.level

}