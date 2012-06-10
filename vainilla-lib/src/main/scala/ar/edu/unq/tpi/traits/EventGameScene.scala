package ar.edu.unq.tpi.traits
import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.events.constants.MouseButton
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.events.constants.Key
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import com.uqbar.vainilla.GameComponent

trait EventGameScene extends GameScene {

  val keyPressetListeners = Map[Key, Map[Any, Buffer[FunctionSceneListener]]]()
  val keyReleasedListeners = Map[Key, Map[Any, Buffer[FunctionSceneListener]]]()
  val keyBeingHoldListeners = Map[Key, Map[Any, Buffer[FunctionSceneListener]]]()

  val mousePressetListeners = Map[MouseButton, Map[Any, Buffer[FunctionSceneListener]]]()
  val mouseReleasedListeners = Map[MouseButton, Map[Any, Buffer[FunctionSceneListener]]]()
  val mouseBeingHoldListeners = Map[MouseButton, Map[Any, Buffer[FunctionSceneListener]]]()

  override def updateComponent(component: GameComponent[_, _], deltaState: DeltaState) {
    processListener(component, keyPressetListeners, deltaState, deltaState.isKeyPressed)
    processListener(component, keyReleasedListeners, deltaState, deltaState.isKeyReleased)
    processListener(component, keyBeingHoldListeners, deltaState, deltaState.isKeyBeingHold)

    processListener(component, mousePressetListeners, deltaState, deltaState.isMouseButtonPressed)
    processListener(component, mouseReleasedListeners, deltaState, deltaState.isMouseButtonReleased)
    processListener(component, mouseBeingHoldListeners, deltaState, deltaState.isMouseButtonBeingHold)
    super.updateComponent(component, deltaState)
  }

  def removeKeyPressetListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    removeListener(component, keyPressetListeners, key, listener)
  }
  def removeKeyReleasedListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    removeListener(component, keyReleasedListeners, key, listener)
  }
  def removeKeyBeingHoldListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    removeListener(component, keyBeingHoldListeners, key, listener)
  }

  def removeMousePressetListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    removeListener(component, mousePressetListeners, button, listener)
  }
  def removeMouseReleasedListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    removeListener(component, mouseReleasedListeners, button, listener)
  }
  def removeMouseBeingHoldListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    removeListener(component, mouseBeingHoldListeners, button, listener)
  }

  def addKeyPressetListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    addListener(component, keyPressetListeners, key, listener)
  }
  def addKeyReleasedListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    addListener(component, keyReleasedListeners, key, listener)
  }
  def addKeyBeingHoldListener(component: Any, key: Key, listener: FunctionSceneListener) = {
    addListener(component, keyBeingHoldListeners, key, listener)
  }

  def addMousePressetListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    addListener(component, mousePressetListeners, button, listener)
  }
  def addMouseReleasedListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    addListener(component, mouseReleasedListeners, button, listener)
  }
  def addMouseBeingHoldListener(component: Any, button: MouseButton, listener: FunctionSceneListener) = {
    addListener(component, mouseBeingHoldListeners, button, listener)
  }

  protected def processListener[A](component: Any, listeners: Map[A, Map[Any, Buffer[FunctionSceneListener]]], deltaState: DeltaState, method: (A) => Boolean) {
    listeners.keys.foreach(key => {
      if (method(key)) {
        if (listeners(key).contains(component)) {
          listeners(key)(component).foreach(
            listener => listener(deltaState))
        }
      }
    })
  }

  protected def addListener[E](component: Any, map: Map[E, Map[Any, Buffer[FunctionSceneListener]]], e: E, listener: FunctionSceneListener) {
    if (!map.contains(e)) {
      map(e) = Map()
    }

    if (!map(e).contains(component)) {
      map(e)(component) = Buffer()
    }
    map(e)(component).append(listener)
  }

  protected def removeListener[E](component: Any, map: Map[E, Map[Any, Buffer[FunctionSceneListener]]], e: E, listener: FunctionSceneListener) {
    if (map.contains(e) && map(e).contains(component)) {
      map(e)(component) -= (listener)
    }

  }

}