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
}

object GameImage extends TraitResources{
  
  lazy val LIFE_BAR = sprite("barra.png")
  lazy val WIN_IMAGE = getImage("win.png")
  lazy val LOSE_IMAGE = getImage("lose.png")
  lazy val BUTTON_START = sprite("start.png")
  lazy val SWORD = sprite("sword.png")
  lazy val LOADING = sprite("loading.png")
  lazy val ROUND_1 = getImage("round1.png")
  lazy val ROUND_2 = getImage("round2.png")
  lazy val ROUND_FINISH = getImage("roundfinal.png")
}