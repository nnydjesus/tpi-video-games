package ar.unq.tpi.collide
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.appearances.Appearance
import java.awt.image.BufferedImage
import java.awt.geom.Point2D
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import ar.unq.tpi.components.GenericGameEvents

trait Colicionable[SceneType <: GameScene, A <: Appearance] extends GameComponent[SceneType, A] {
  def getImage(): BufferedImage
  def width: Double
  def height: Double
  var inColition = true

  def isClickMe(mousePosition: Point2D.Double): Boolean = {
    return (this.getX() <= mousePosition.getX() && this.getX() + this.getAppearance().getWidth() >= mousePosition.getX()) &&
      (this.getY() <= mousePosition.getY() && this.getY() + this.getAppearance().getHeight() >= mousePosition.getY())
  }
}

trait PixelPerfectCollition[SceneType <: GameScene, A <: Appearance] extends Colicionable[SceneType, A] { 
  val pixeles = 5
  var positionOnCollition:Tuple2[Int, Int] =null

  //pixel perfect

  def collidesWithTop(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(topIntersection), topPixelPerfect)

  }

  def collidesWithBotton(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(bottonIntersection), bottonPixelPerfect)
  }

  def collidesWithLeft(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(leftIntersection), leftPixelPerfect)
  }

  def collidesWithRigth(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(rigthIntersection), rigthPixelPerfect)
  }

  def collidesWithRigthTop(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(rigthTopIntersection), rigthPixelPerfect)
  }

  def collidesWithLeftTop(component: Colicionable[_, _]): Boolean = {
    return collidesWith(component, intersection(leftTopIntersection), rigthPixelPerfect)
  }

  def collidesWith(component: Colicionable[_, _]): Boolean = {
    this.collidesWith(component, intersection(allIntersection), pixelPerfect)
  }

  def collidesWith(component: Colicionable[_, _], intersection: (Colicionable[_, _] => Boolean), pixelPerfect: (Colicionable[_, _] => Boolean)): Boolean = {
    var isColliding = false;
    positionOnCollition = null

    if (this.inColition && component != null && component.inColition && component.getAppearance() != null && intersection(component)) {
      isColliding = pixelPerfect(component);
    }

    return isColliding;
  }

  def intersection(predicate: (Double, Double, Double, Double) => Boolean): Colicionable[_, _] => Boolean = {
    component =>
      var componentX = component.getX();
      var componentY = component.getY();
      var componentWidth = component.width;
      var componentHeight = component.height;

      predicate(componentX, componentY, componentWidth, componentHeight)
  }

  def allIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return (this.getX() + this.width >= componentX &&
      this.getY() + this.height >= componentY &&
      this.getX() <= componentX + componentW &&
      this.getY() <= componentY + componentH);
  }

  def topIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    val bol = this.getX() + this.width >= componentX &&
      this.getX() <= componentX + componentW &&
      componentY + componentH >= this.getY() &&
      componentY + componentH <= this.getY()  + pixeles
    return bol
  }

  def bottonIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return (this.getY() + this.height >= componentY &&
      this.getX() <= componentX + componentW &&
      this.getY() <= componentY + componentH);
  }

  def leftIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return this.getX() == (componentX + componentW) &&
      this.getY() + this.height >= componentY &&
      this.getY() <= componentY + componentH;
  }

  def rigthIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return this.getX() + this.width == componentX &&
      this.getY() + this.height >= componentY &&
      this.getY() <= componentY + componentH;
  }

  def rigthTopIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return this.topIntersection(componentX, componentY, componentW, componentH) && this.rigthIntersection(componentX, componentY, componentW, componentH)
  }

  def leftTopIntersection(componentX: Double, componentY: Double, componentW: Double, componentH: Double): Boolean = {
    return this.topIntersection(componentX, componentY, componentW, componentH) && this.leftIntersection(componentX, componentY, componentW, componentH)
  }

  def pixelPerfect(component: Colicionable[_, _]): Boolean = {
    return basicPixelPerfect(component, (x) => 1, (y) => 1, (toX) => toX, (toY) => toY)
  }

  def topPixelPerfect(component: Colicionable[_, _]): Boolean = {
    return basicPixelPerfect(component, (x) => 1, (y) => 1, (toX) => toX, (toY) => Math.min(pixeles, toY))
  }

  def bottonPixelPerfect(component: Colicionable[_, _]): Boolean = {

    var indexY = (y: Int) => {
      var indexY = y - pixeles
      if (y <= pixeles) {
        indexY = 1
      }
      indexY
    }

    return basicPixelPerfect(component, (x) => 1, indexY, (toX) => toX, (toY) => toY)
  }

  def leftPixelPerfect(component: Colicionable[_, _]): Boolean = {
    return basicPixelPerfect(component, (x) => 1, (y) => 1, (toX) => Math.min(pixeles, toX), (toY) => toY)
  }

  def rigthPixelPerfect(component: Colicionable[_, _]): Boolean = {
    var toX = (x: Int) => {
      var indexX = x - pixeles
      if (x <= pixeles) {
        indexX = 1
      }
      indexX
    }
    return basicPixelPerfect(component, toX, (y) => 1, (toX) => toX, (toY) => toY)
  }

  def basicPixelPerfect(component: Colicionable[_, _], indexX: (Int) => Int, indexY: (Int) => Int, toX: (Int) => Int, toY: (Int) => Int): Boolean = {
    // initialization
    var myWidth = this.getX() + this.getImage().getWidth() - 1
    var myHeight = this.getY() + this.getImage().getHeight() - 1
    var compWidth = component.getX() + component.width - 1
    var compheight = component.getY() + component.height - 1

    var maxX = Math.max(this.getX(), component.getX())
    var maxY = Math.max(this.getY(), component.getY())
    var minWidth = Math.min(myWidth, compWidth)
    var minHeight = Math.min(myHeight, compheight)

    // intersection rect
    var totalY = Math.abs(minHeight - maxY).toInt
    var totalX = Math.abs(minWidth - maxX).toInt

    for (y <- indexY(totalY) until toY(totalY)) {

      var ny = (Math.abs(maxY - this.getY()) + y).toInt
      var ny1 = (Math.abs(maxY - component.getY()) + y).toInt

      for (x <- indexX(totalX) until toX(totalX)) {

        var nx = (Math.abs(maxX - this.getX()) + x).toInt
        var nx1 = (Math.abs(maxX - component.getX()) + x).toInt

        if (((this.getImage().getRGB(nx, ny) & 0xFF000000) != 0x00) &&
          ((component.getImage().getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {
          // collide!!
          positionOnCollition = Tuple2(nx, ny)
          return true;
        }
      }
    }

    return false;
  }
  
  
  //////////////////////////////
  ////////////INPACT////////////
  //////////////////////////////
  
  def impactInCenter(component:Colicionable[_,_], delta:Double):Boolean ={
    return (this.getX() + this.width / 2) > ((component.getX() + component.width / 2) - delta) && 
    (this.getX() + this.width / 2) < ((component.getX() + component.width / 2) + delta) 
  }
  
  def impactLeft(component:Colicionable[_,_]):Boolean ={
    return (this.getX() + this.width / 2) < (component.getX() + component.width / 2)
  }
  
  def impactRight(component:Colicionable[_,_]):Boolean ={
    return (this.getX() + this.width / 2) > (component.getX() + component.width / 2)
  }
  
  def impactTop(component:Colicionable[_,_]):Boolean ={
    return (this.getY() + this.height) >= (component.getY()) && (this.getY() + this.height) <= (component.getY() + (component.height / 2))
  }
  
  def impactBotton(component:Colicionable[_,_]):Boolean ={
    return (this.getY()) <= (component.getY() + component.height) && (this.getY()) >= (component.getY() + (component.height /2) )
  }

}