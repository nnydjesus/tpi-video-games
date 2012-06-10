package ar.unq.tpi.collide
import java.awt.Color
import java.awt.Graphics2D
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.appearances.Rectangle

class Bounds(color:Color, width:Int, height:Int) extends Rectangle(color, width, height){
  
  	override def render(component:GameComponent[_, _] ,graphics: Graphics2D ) ={
//  		graphics.setColor(this.color);
		graphics.drawRect(component.getX().toInt, component.getY().toInt, this.width, this.height);
  	}

}