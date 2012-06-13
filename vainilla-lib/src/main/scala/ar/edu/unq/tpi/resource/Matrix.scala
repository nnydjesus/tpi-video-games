package ar.edu.unq.tpi.resource

import scala.collection.mutable.Map

class Matrix[T](var width:Int, var height:Int) {
  
 val elems =  Map[Int, Map[Int, T]]()
  init()
  
  def this(){
    this(0,0)
  }
  
  private def init(){
    for(i <- 0 until width if i < width){
      for(j <- 0 until height if i < height){
        this.put(i, j, null.asInstanceOf[T])
      }
    }
  }
  
  println("matrix" + elems )
  val rows = elems.size        //Returns # of rows

  def apply[B >: T](i:Int, j:Int) = elems(i)(j)
  def apply(i: Int) = elems(i)
  
  def put[B <:T](i: Int, j: Int, t:B) {
    if(!elems.contains(i)){
      elems(i)= Map()
    }
    elems(i)(j) = t
  }
  
   def includeKeys(x: Int, y: Int): Boolean = {
    var include: Boolean = false
    elems.keys.foreach(i => {
      elems(i).keys.foreach(j => {
        if (i == x && j == y) {
          include = true
        }
      })
    })
    return include
  }
  
  def isOverflow(x: Int, y: Int): Boolean = {
    return ! includeKeys(x,y)
    }

}
