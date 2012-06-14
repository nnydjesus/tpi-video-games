package ar.edu.unq.tpi
import ar.unq.tpi.components.SpriteComponent
import com.uqbar.vainilla.appearances.Sprite
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import ar.unq.tpi.components.DynamicLabelComponent
import ar.unq.tpi.components.LabelComponent
import java.awt.Color
import ar.edu.unq.tpi.traits.Event


class Hud(scene: GamePlayScene) {

  val pading = 5
  val initDeltaBorder = 90

  val bar1 = new LifeBar(GameImage.LIFE_BAR.flipHorizontally(), GameImage.BACKGROUND_BAR.flipHorizontally(), GameImage.HUD_BAR_1.flipHorizontally(), GameImage.HUD_BAR_2.flipHorizontally(), scene.character1.character.envelope.selectedImage.scale(80D / 100, 100D / 160), true, (scene.getGame().getDisplayWidth() / 2) - GameImage.BACKGROUND_BAR.getWidth() + 30, 0, scene.character1.character.getLife)
  var bar2 = new LifeBar(GameImage.LIFE_BAR, GameImage.BACKGROUND_BAR, GameImage.HUD_BAR_1, GameImage.HUD_BAR_2, scene.character2.character.envelope.selectedImage.scale(80D / 100, 100D / 160), false, bar1.getX() + GameImage.BACKGROUND_BAR.getWidth() - 30, 0, scene.character2.character.getLife)
  var timer = new Timer(100, 1,Fonts.GODOFWAR.deriveFont(60f), Color.YELLOW,  bar2.getX()-(GameImage.BACKTIMER.getWidth()/2)+40, 40)
  var listPoints1 = Buffer[StatusRound]()
  var listPoints2 = Buffer[StatusRound]()
  

  def init() {
    listPoints1.append(new StatusRound(bar1.getX() + initDeltaBorder, GameImage.BACKGROUND_BAR.getHeight() - 30))
    listPoints1.append(new StatusRound(bar1.getX() + initDeltaBorder + GameImage.LOSE_ROUND.getWidth() + pading, GameImage.BACKGROUND_BAR.getHeight() - 30))

    listPoints2.append(new StatusRound(bar2.getX() + GameImage.BACKGROUND_BAR.getWidth() - 40 - initDeltaBorder, GameImage.BACKGROUND_BAR.getHeight() - 30))
    listPoints2.append(new StatusRound(bar2.getX() + GameImage.BACKGROUND_BAR.getWidth() - 40 - initDeltaBorder - GameImage.LOSE_ROUND.getWidth() - pading, GameImage.BACKGROUND_BAR.getHeight() - 30))
    
    scene.addComponent(new SpriteComponent[GamePlayScene](GameImage.BACKTIMER, bar2.getX()-(GameImage.BACKTIMER.getWidth()/2)+5, 20))
    scene.addComponents(bar1, bar2)
    scene.addComponents(listPoints1)
    scene.addComponents(listPoints2)
    scene.addComponents(new DynamicLabelComponent[GamePlayScene](scene.character1.character.getScore, Fonts.GODOFWAR.deriveFont(20f), Color.YELLOW,bar1.getX()+(GameImage.BACKTIMER.getWidth()/2)+200 , 40))
    scene.addComponents(new DynamicLabelComponent[GamePlayScene](scene.character2.character.getScore, Fonts.GODOFWAR.deriveFont(20f), Color.YELLOW, bar2.getX()+(GameImage.BACKTIMER.getWidth())+100 , 40))
    
    scene.addComponents(new LabelComponent[GamePlayScene](scene.character1.character.envelope.name, Fonts.GODOFWAR.deriveFont(40f), Color.YELLOW,bar1.getX()+(GameImage.BACKTIMER.getWidth()/2)-5 , 90))
    scene.addComponents(new LabelComponent[GamePlayScene](scene.character2.character.envelope.name, Fonts.GODOFWAR.deriveFont(40f), Color.YELLOW, bar2.getX()+(GameImage.BACKTIMER.getWidth())+400 , 90))
    addTimer()
  }
  
  def addTimer(){
    scene.addComponent(timer)
    
    timer.addEventListener(GameEvents.FINISH_TIME, onFinishTime)
  }
  
  def onFinishTime: (Event[Timer, Any]) => Unit = event=>{
    scene.timeOut()
    stop()
  }
  
  def start() = timer.start()
  def stop() = timer.stop()

  def indexStatusPointer: Int = scene.state match {
    case FirstRound => 0
    case SecondRound => 1
    case _ => -1
  }

  def addvictoy1() {
    var index = indexStatusPointer
    if (index != -1) {
      listPoints1(index).changeWin
      listPoints2(index).changeLose
    }
    //    new SpriteComponent(GameImage.WIN_IMAGE, bar1.getX() + initDeltaBorder )
  }

  def addvictoy2() {
    var index = indexStatusPointer
    if (index != -1) {
      listPoints2(index).changeWin
      listPoints1(index).changeLose
    }
  }
  
  def empate() {
    var index = indexStatusPointer
    if (index != -1) {
      listPoints2(index).changeLose
      listPoints1(index).changeLose
    }
  }

}

class StatusRound(x: Double, y: Double) extends SpriteComponent[GamePlayScene](GameImage.NONE_ROUND, x, y) {
  def changeWin = setAppearance(GameImage.WIN_ROUND)
  def changeLose = setAppearance(GameImage.LOSE_ROUND)
  def changeNone = setAppearance(GameImage.NONE_ROUND)
}