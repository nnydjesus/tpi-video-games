package ar.edu.unq.tpi
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.Color
import java.awt.Dimension

import scala.swing.TabbedPane.Page
import scala.swing.event.FocusGained
import scala.swing.event.FocusLost
import scala.swing.event.KeyPressed
import scala.swing.Action
import scala.swing.BorderPanel
import scala.swing.Button
import scala.swing.Dialog
import scala.swing.GridBagPanel
import scala.swing.Label
import scala.swing.Reactor
import scala.swing.TabbedPane
import scala.swing.TextField

import com.uqbar.vainilla.events.constants.Key

import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.Player
import ar.edu.unq.tpi.PlayerKey

class ConfigureButtonsDialog() extends Dialog {

  title = "Configuracion de Botones"
    
  contents = new BorderPanel() {
	  
    add(new TabbedPane {
      pages += new Page("Player1", new PlayerPanel(Player1))
      pages += new Page("Player2", new PlayerPanel(Player2))
    }, BorderPanel.Position.North)

    add(new Button(new Action("save") { def apply() = { Player1.saveConf(); Player2.saveConf(); close()} }), BorderPanel.Position.Center) 
  }

  open()
}

class PlayerPanel(player: Player) extends GridBagPanel {
  size = new Dimension(300, 400)
  val gbc = new Constraints()
  gbc.gridx = 0
  gbc.gridy = 0

  var i = 0
  player.keys.foreach(key => {
    gbc.gridy = i
    gbc.gridx = 0
    add(new Label(key.name) { font = Fonts.GODOFWAR.deriveFont(20f) }, gbc)
    gbc.gridx = 1
    val text = new TextField("-----------------") {
      editable = false
      preferredSize = new Dimension(300, 50)
      font = Fonts.GODOFWAR.deriveFont(40f)
      background = Color.LIGHT_GRAY
    }
    Binder.bind(key, () => key.name, text)
    add(text, gbc)
    i += 1
  })

  gbc.gridx = 3
  gbc.gridy = gbc.gridy + 3
  //    centerOnScreen()
}

object Configure {
  def main(args: Array[String]) {
    new ConfigureButtonsDialog()
    //    UIDemo.startup(args)
  }
}
object Binder extends Reactor {
  def bind(key: PlayerKey, get: () => String, textField: TextField) {
    var locked = false

    updateText(key)
    key.addEventListener("key", (e: Event[PlayerKey, Key]) => updateText(e.target))

    listenTo(textField)
    reactions += {
      case KeyPressed(textField, _, _, _) => {
        println("sdfsd")
        //        key.setKey(Key.fromKeyCode(event.getKeyCode()))
      }
      case FocusGained(textField, _, _) => {
        textField.background = Color.RED
      }
      case FocusLost(textField, _, _) => {
        textField.background = Color.LIGHT_GRAY
      }
    }
    //    

    def updateText(key: PlayerKey) {
      textField.text = key.key.getCode();
    }

    textField.peer.addKeyListener(new KeyAdapter {
      override def keyPressed(event: KeyEvent) {
        event.consume()
        key.setKey(Key.fromKeyCode(event.getKeyCode()))
      }
    })

  }
}