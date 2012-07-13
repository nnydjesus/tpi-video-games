package ar.unq.tpi.nny.pacman.domain
import java.lang.Object

class Position(var row:Double, var column:Double) {

	def addToPosition(position:Position) {
		this.row += position.row
		column += position.column
	}
	
	def removeToPosition(position:Position) {
		this.row -= position.row
		this.column -= position.column
	}
	
	override def clone() =  new Position(row, column)

	override def hashCode():Int = {
		var prime = 31
		var result = 1
		result = (prime * result + column).toInt
		result = (prime * result + row).toInt
		return result
	}
	
	override def equals(obj:Any):Boolean = {
		if (obj == null) return false
		if (getClass() != obj.getClass())	return false
		
		obj match {
		  case other:Position => {
			  if (column != other.column)
				  return false
			  if (row != other.row)
				  return false
		  }
		}
		return true
	}
	
}
