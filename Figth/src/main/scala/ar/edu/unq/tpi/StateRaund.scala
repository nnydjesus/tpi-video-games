package ar.edu.unq.tpi
import ar.unq.tpi.components.Selectable
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import resource.SpriteUtil
import com.uqbar.vainilla.Game
import ar.unq.tpi.components.CenterComponent
import ar.unq.tpi.components.AnimateSprite

abstract class StateRaund(var animationRound:AnimateSprite ) {
  def symbolOfVictory: Sprite = SpriteUtil.sprite("symbol.png")
  def stepNext(scene: GamePlayScene)
}

object FirstRaund extends StateRaund(new AnimateSprite(GameImage.ROUND_1)) {

  def stepNext(scene: GamePlayScene) {
    scene.startRound(SecondRaund)
  }
}
object SecondRaund extends StateRaund(new AnimateSprite(GameImage.ROUND_2)) {

  def stepNext(scene: GamePlayScene) {
    if (scene.countVictorysChF >= GameValues.VICTORYS_TO_WIN || scene.countVictorysChS >= GameValues.VICTORYS_TO_WIN) {
    	scene.finishFigth()
    } else {
    	scene.startRound(LastRaund)
    }
  }
}
object LastRaund extends StateRaund(new AnimateSprite(GameImage.ROUND_FINISH)) {
  def stepNext(scene: GamePlayScene) {
    scene.finishFigth()
  }
}