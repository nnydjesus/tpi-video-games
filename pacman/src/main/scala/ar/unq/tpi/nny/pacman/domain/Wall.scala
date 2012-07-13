package ar.unq.tpi.nny.pacman.domain
import ar.unq.tpi.nny.pacman.domain.TraversableBlock

class Wall extends NotTraversableBlock {
	override def toString() = "W";
}

class SimpleWall extends NotTraversableBlock {
	override def toString() = "W";
}


class Floor extends TraversableBlock {
  
  def this(item:Item){
    this()
    this.addItem(item)
  }

}
