package ar.edu.unq.tpi.traits

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

trait EventGameComponent[T <: EventGameComponent[T]] {

  private var listeners = Map[String, Buffer[FunctionEvent[T, _]]]()

  protected def dispatchEvent[A <: Any](event: Event[T, A]) {
    listeners.keys.foreach(key => {
      if (key.equals(event.name)) {
        listeners(key).foreach(listener => listener.apply(event))
      }
    })
  }

  def addEventListener[A <: Any](eventName: String, listener: FunctionEvent[T, A]) {
    if (!listeners.contains(eventName)) {
      listeners(eventName) = Buffer()
    }
    listeners(eventName).append(listener)

  }

  def addEventListener[A <: Any](eventName: String, listener: (Event[T, A]) => Unit) {
    this.addEventListener(eventName, new FunctionEvent(listener))
  }

  def removeEventListener[A <: Any](eventName: String, listener: FunctionEvent[T, A]) {
    if (listeners.contains(eventName)) {
      listeners(eventName).-=(listener)
//      listeners(eventName).clear()
    }
  }

  def removeEventListener[A <: Any](eventName: String, listener: (Event[T, A]) => Unit) {
    this.removeEventListener(eventName, new FunctionEvent(listener))
  }

}

