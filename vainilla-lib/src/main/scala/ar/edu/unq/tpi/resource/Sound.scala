package ar.edu.unq.tpi.resource
import javazoom.jl.player.advanced.AdvancedPlayer

class Sound(path: String) extends TraitResources {

  var player: AdvancedPlayer = null
  var playerThread: Thread = null

  def play() = {
	player = new AdvancedPlayer(fileSound(path))
    playerThread = new Thread() {
      override def run() {
        player.play()
      }
    }
    playerThread.start()
  }
  
  def pause{
    playerThread.stop()
  }

  def stop = {
	player.close()
    playerThread.interrupt()
  }

}