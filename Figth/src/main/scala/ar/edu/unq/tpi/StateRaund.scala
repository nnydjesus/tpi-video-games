package ar.edu.unq.tpi
import ar.unq.tpi.components.Selectable
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import resource.SpriteUtil
import com.uqbar.vainilla.Game
import ar.unq.tpi.components.AnimateSprite
import ar.unq.tpi.components.CenterComponent

abstract class StateRaund {
	
  var countVictorysChF : Int = 0
  var countVictorysChS : Int = 0
  def symbolOfVictory : Sprite = SpriteUtil.sprite("symbol.png")
  def isFirstWin() = countVictorysChF > countVictorysChS
  def stepNext(fight : Fight)
}

object FirstRaund extends StateRaund {

  def stepNext(fight : Fight){
  fight.state = SecondRaund
  }
}
object SecondRaund extends StateRaund{

  def stepNext(fight : Fight){
   fight.state = LastRaund
  }
}
object LastRaund extends StateRaund{
  def stepNext(figth : Fight){
  //
  }
}