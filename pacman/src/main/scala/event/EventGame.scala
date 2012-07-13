package event
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

/**
 *Hago esto porque tengo problemas con el import en el board
 */
trait EventGame[T <: EventGameComponent[T]] extends EventGameComponent[T]{
  
}
class DEvent[A, E](name:String, target:A, data:E=null) extends  Event[A, E](name, target, data) {
  
}