package ar.unq.tpi.nny.pacman.util

import ar.edu.unq.tpi.resource.TraitResources

object Fonts extends TraitResources {
  val PAC_FONT = getFont("PacFont Good.ttf")
  val GODOFWAR = getFont("GODOFWAR.TTF")
  val CRACKMAN = getFont("crackman.ttf")
}

object GameConstants {
  val TILE = 20
  val SIZE_PIXEL_MAP_DENSITY = 10
  val MAP_DENSITY_NAME = "map.png"
  val MAP_TEXT_NAME = "/map/map1.txt"
  val MAP_DENSITY_TYPE = "png"
}

object GameEvents {

  val COLLIDE_PACMAN_WITH_STAR = "COLLIDE_PACMAN_WITH_STAR"
  val COLLIDE_PACMAN_WITH_HEART = "COLLIDE_PACMAN_WITH_HEART"
  val COLLIDE_PACMAN_WITH_GHOST = "COLLIDE_PACMAN_WITH_GHOST"
  val PLACE_CHERRY = "PLACE_CHERRY"
  val REMOVE_CHERRY = "REMOVE_CHERRY"
  val START = "START"

}

object GameImages extends TraitResources {

  lazy val SHOT1 = sprite("shot1.png")
  lazy val BLUE_GHOST = sprite("Blue Ghost.png")
  lazy val DEATH_PACMAN = sprite("Pac-Man die.png")
  lazy val PILL = sprite("pill.png")
  lazy val POWER_PILL = sprite("powerPill.png")
  lazy val EYE_GHOST = sprite("eye.png")
  lazy val CHERRY = sprite("cherry.png")
  lazy val LABEL = sprite("labelImage.png")
  lazy val INIT = sprite("init.jpg")

  lazy val BORDER_Double1 = sprite("border2/double1.png")
  lazy val BORDER_Double2 = sprite("border2/double2.png")
  lazy val BORDER_Double3 = sprite("border2/double3.png")

  lazy val BORDER_Simple1 = sprite("border2/simple1.png")
  lazy val BORDER_Simple2 = sprite("border2/simple2.png")
  lazy val BORDER_Simple3 = sprite("border2/simple3.png")
  lazy val BORDER_Simple4 = sprite("border2/simple4.png")
}