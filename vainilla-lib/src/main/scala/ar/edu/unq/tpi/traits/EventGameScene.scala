package ar.edu.unq.tpi.traits
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.uqbar.vainilla.GameComponent

trait EventGameScene extends GameScene {

  val keyPressetListeners = Map[List[Key], Map[Any, Buffer[FunctionSceneListener]]]()
  val keyReleasedListeners = Map[List[Key], Map[Any, Buffer[FunctionSceneListener]]]()
  val keyBeingHoldListeners = Map[List[Key], Map[Any, Buffer[FunctionSceneListener]]]()

  val mousePressetListeners = Map[List[MouseButton], Map[Any, Buffer[FunctionSceneListener]]]()
  val mouseReleasedListeners = Map[List[MouseButton], Map[Any, Buffer[FunctionSceneListener]]]()
  val mouseBeingHoldListeners = Map[List[MouseButton], Map[Any, Buffer[FunctionSceneListener]]]()

  override def updateComponent(component: GameComponent[_, _], deltaState: DeltaState) {
    processListener(component, keyPressetListeners, deltaState, deltaState.isKeyPressed)
    processListener(component, keyReleasedListeners, deltaState, deltaState.isKeyReleased)
    processListener(component, keyBeingHoldListeners, deltaState, deltaState.isKeyBeingHold)

    processListener(component, mousePressetListeners, deltaState, deltaState.isMouseButtonPressed)
    processListener(component, mouseReleasedListeners, deltaState, deltaState.isMouseButtonReleased)
    processListener(component, mouseBeingHoldListeners, deltaState, deltaState.isMouseButtonBeingHold)
    super.updateComponent(component, deltaState)
  }

  def removeKeyPressetListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    removeListener(component, keyPressetListeners, listener, keys.toList)
  }
  def removeKeyReleasedListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    removeListener(component, keyReleasedListeners, listener, keys.toList)
  }
  def removeKeyBeingHoldListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    removeListener(component, keyBeingHoldListeners, listener, keys.toList)
  }

  def removeMousePressetListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    removeListener(component, mousePressetListeners, listener, button.toList)
  }
  def removeMouseReleasedListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    removeListener(component, mouseReleasedListeners, listener, button.toList)
  }
  def removeMouseBeingHoldListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    removeListener(component, mouseBeingHoldListeners, listener, button.toList)
  }

  def addKeyPressetListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    addListener(component, keyPressetListeners, listener, keys.toList)
  }
  def addKeyReleasedListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    addListener(component, keyReleasedListeners, listener, keys.toList)
  }
  def addKeyBeingHoldListener(component: Any, listener: FunctionSceneListener, keys: Key*) = {
    addListener(component, keyBeingHoldListeners, listener, keys.toList)
  }

  def addMousePressetListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    addListener(component, mousePressetListeners, listener, button.toList)
  }
  def addMouseReleasedListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    addListener(component, mouseReleasedListeners, listener, button.toList)
  }
  def addMouseBeingHoldListener(component: Any, listener: FunctionSceneListener, button: MouseButton*) = {
    addListener(component, mouseBeingHoldListeners, listener, button.toList)
  }

  protected def processListener[A<:List[B], B](component: Any, listeners: Map[A, Map[Any, Buffer[FunctionSceneListener]]], deltaState: DeltaState, method: (B) => Boolean) {
    listeners.keys.foreach(keys => {
      var satisfy = keys.foldLeft(true)((a, j) => a && method(j))
      if (satisfy) {
        if (listeners(keys).contains(component)) {
          listeners(keys)(component).foreach(
            listener => listener(deltaState))
        }
      }
    })
  }

  protected def addListener[E](component: Any, map: Map[List[E], Map[Any, Buffer[FunctionSceneListener]]],  listener: FunctionSceneListener, e: List[E]) {
    if (!map.contains(e)) {
      map(e) = Map()
    }

    if (!map(e).contains(component)) {
      map(e)(component) = Buffer()
    }
    map(e)(component).append(listener)
  }

  protected def removeListener[E](component: Any, map: Map[List[E], Map[Any, Buffer[FunctionSceneListener]]],  listener: FunctionSceneListener, e: List[E]) {
    if (map.contains(e) && map(e).contains(component)) {
      map(e)(component) -= (listener)
    }

  }

}

