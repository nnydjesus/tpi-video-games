package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Animation
import scala.collection.mutable.HashMap
import com.uqbar.vainilla.appearances.Sprite
import ar.edu.unq.tpi.resource.TraitResources

class Character(var envelope:CharacterAppearance){

  var death = false

  var life = 100D

  def getLife() = this.life
  
  
  def getMove(move: Movement) = envelope.getMove(move)

  def receiveAttack(force: Double) {
    life  -= force
    if(life <= 0){
      this.death = true
    }
  }

}
