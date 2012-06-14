package ar.edu.unq.tpi
import ar.unq.tpi.components.LabelComponent
import java.awt.Font
import java.awt.Color
import com.uqbar.vainilla.DeltaState
import traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event

class Timer(val totalTime: Int, val step: Int, font: Font, color: Color, x: Double, y: Double) extends LabelComponent[GamePlayScene](totalTime.toString(), font, color, x, y) with EventGameComponent[Timer] {

  var currentTime = totalTime
  var remainigTime = 0D
  var running = true
  

  def start() = {
    running = true
    currentTime = totalTime
    remainigTime = 0D
  }
  def stop() = running = false

  override def update(state: DeltaState) {
    super.update(state)
    if (running) {
      remainigTime -= state.getDelta()
      if (remainigTime <= 0) {
        remainigTime = step
        currentTime -= step
      }

      getAppearance().setText(currentTime.toString())

      if (currentTime <= 0) {
        dispatchEvent(new Event(GameEvents.FINISH_TIME, this, null))
      }
    }

  }

}
