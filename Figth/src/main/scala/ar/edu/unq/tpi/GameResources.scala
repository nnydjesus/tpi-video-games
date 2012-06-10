package ar.edu.unq.tpi
import resource.TraitResources

object GameEvents {

  val COLLIDE_WITH_BOUND_LEFT = "COLLIDE_WITH_BOUND_LEFT"
  val COLLIDE_WITH_BOUND_RIGTH = "COLLIDE_WITH_BOUND_RIGTH"
  val DEATH = "DEATH"
  val FINISH_FIGTH= "FINISH_FIGTH"
   val FINISH_ANIMATION = "FINISH_ANIMATION" 

}


object GameValues {
  
  val PLAYER_LIFE = 100D
  val VICTORYS_TO_WIN = 2
  val DELTA_BACK_MOVE = 15
}

object GameImage extends TraitResources{
  lazy val LIFE_BAR = sprite("hud/cp15_lifegage_Y.png")
  lazy val BACKGROUND_BAR = sprite("hud/cp15_main03.png")
  lazy val HUD_BAR_1 = sprite("hud/cp15_ee.png")
  lazy val HUD_BAR_2 = sprite("hud/cp15_dd.png")
  lazy val WIN_IMAGE = getImage("win.png")
  lazy val LOSE_IMAGE = getImage("lose.png")
  lazy val BUTTON_START = sprite("start.png")
  lazy val SWORD = sprite("sword.png")
  lazy val LOADING = sprite("loading.png")
  lazy val ROUND_1 = getImage("round1.png")
  lazy val ROUND_2 = getImage("round2.png")
  lazy val ROUND_FINISH = getImage("roundfinal.png")
  lazy val COLLITION = sprite("vrrgef450atk_01.png")
}