package ar.unq.tpi.nny.pacman.map

import scala.collection.mutable.Map
import ar.unq.tpi.nny.pacman.scene.PlayScene
import ar.unq.tpi.nny.pacman.util.GameImages._
import java.awt.Color
import java.awt.Color._
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.Graphics

object Token{
  private val values = Map[String, Token]()
  private val valuesByColor = Map[Color, Token]()
  Wall.productArity
  SWall.productArity
  Space.productArity
  Star.productArity
  Heart.productArity
  Pacman.productArity
  Pinky.productArity
  Blinky.productArity
  Inky.productArity
  Clyde.productArity
  
  def addValue(name:String, value:Token) {
    values(name)=value
  }
  
  def addColor(color:Color, value:Token) {
    valuesByColor(color)=value
  }
  
  def value(name:String) = values(name)
  
  def valueByColor(color:Color) = valuesByColor(color)
}
 case class Token(var color:Color, var action:(PlayScene, Int, Int)=>Unit){
  
  def this(name:String, color:Color, action:(PlayScene, Int, Int)=>Unit){
    this(color, action)
    Token.addValue(name, this)    
    Token.addColor(color, this)    
  }
  
  def draw(x:Int, y:Int, image:Graphics, size:Int){
    image.setColor(this.color)
    image.fillRect(x*size, y*size, size, size)
  }
}

case object Wall extends Token("w", BLACK, (scene, x, y)=> scene.addWall(x, y))
case object SWall extends Token("v", GRAY,  (scene, x, y)=> scene.addSimpleWall(x, y))
case object Space extends Token("_", WHITE,  (scene, x, y)=> scene.addFlor(x, y))
case object Star extends Token("*", YELLOW, (scene, x, y)=> scene.addStar(x, y))
case object Heart extends Token("+", MAGENTA, (scene, x, y)=> scene.addHeart(x, y))
case object Pacman extends Token("p", GREEN,  (scene, x, y)=> scene.addPacman(x, y))
case object Pinky extends Token("P", PINK, (scene, x, y)=> scene.addPinky(x, y))
case object Blinky extends Token("B", RED, (scene, x, y)=> scene.addBlinky(x, y))
case object Inky extends Token("I", CYAN, (scene, x, y)=> scene.addInky(x, y))
case object Clyde extends Token("C",ORANGE,  (scene, x, y)=> scene.addClyde(x, y))
