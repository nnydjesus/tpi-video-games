package ar.edu.unq.tpi.traits

class Function[A, B](val listener: (Event[A, B]) => Unit) {

  def apply(event: Event[A, _]) = event match {
    case e: Event[A, B] => listener(e)
  }
}