package ar.edu.unq.tpi
import resource.TraitResources

object GameEvents {

  val COLLIDE_WITH_BOUND_LEFT = "COLLIDE_WITH_BOUND_LEFT"
  val COLLIDE_WITH_BOUND_RIGTH = "COLLIDE_WITH_BOUND_RIGTH"
  val DEATH = "DEATH"
  val FINISH_FIGTH = "FINISH_FIGTH"
  val FINISH_ANIMATION = "FINISH_ANIMATION"
  val FINISH_TIME = "FINISH_TIME"
  val LOAD_RESOURCE = "LOAD_RESOURCE"
  val CABEZAL_MOVIDO = "CABEZAL_MOVIDO"
  val CABEZAL_SELECTED = "CABEZAL_SELECTED"
  val BACK_SELECT_CHARACTER = "BACK_SELECT_CHARACTER"
  val SELECT_PLAYER = "SELECT_PLAYER"
  val OPTIONS = "OPTIONS"
  val ALL_PLAYER_SELECT_CHARACTER = "ALL_PLAYER_SELECT_CHARACTER"  
 

}

object GameValues {

  val PLAYER_LIFE = 100D
  val VICTORYS_TO_WIN = 2
  val DELTA_BACK_MOVE = 15
  val WIDTH_SELECTED_CHARACTER = 100
  val HEIGHT_SELECTED_CHARACTER = 100
  val PADDING_CHARACTER = 10
  val WIDTH_MATRIX = 6
  val HEIGHT_MATRIX = 6
  val WIDTH_CHARACTER_ANIMATION = 567
  val HEIGHT_CHARACTER_ANIMATION = 454
    
}

object Fonts extends TraitResources {
  val GODOFWAR = getFont("GODOFWAR.TTF")
}

object GameImage extends TraitResources {
  lazy val LIFE_BAR = sprite("hud/cp15_lifegage_Y.png")
  lazy val BACKGROUND_BAR = sprite("hud/cp15_main03.png")
  lazy val HUD_BAR_1 = sprite("hud/cp15_ee.png")
  lazy val HUD_BAR_2 = sprite("hud/cp15_dd.png")
  lazy val WIN_ROUND = sprite("hud/win.png")
  lazy val LOSE_ROUND = sprite("hud/lose.png")
  lazy val NONE_ROUND = sprite("hud/none.png")
  lazy val BACKTIMER = sprite("hud/backTimer.png")
  lazy val WIN_IMAGE = getImage("win.png")
  lazy val LOSE_IMAGE = getImage("lose.png")
  lazy val INIT_BUTTON = sprite("button-bg.png")
  lazy val INIT_BUTTON_OVER = sprite("button-over.png")
  lazy val LOGO = sprite("init-logo.png")
  lazy val BUTTON_START = sprite("start.png")
  lazy val SWORD = sprite("sword.png")
  lazy val LOADING = sprite("vsinfo.png")
  lazy val LOADING_B = sprite("vsinfo-2.png")
  lazy val ROUND_1 = getImage("round1.png")
  lazy val ROUND_2 = getImage("round2.png")
  lazy val ROUND_FINISH = getImage("roundfinal.png")
  lazy val COLLITION = sprite("collition/vrrgef450atk_01.png")
}