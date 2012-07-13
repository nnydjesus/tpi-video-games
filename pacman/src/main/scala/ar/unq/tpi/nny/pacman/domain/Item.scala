package ar.unq.tpi.nny.pacman.domain

trait Item {
	var name = ""
	def doAction(creature:Creature ):Int;
}

class Pill extends Item{
	val POINTS = 10
	def doAction(creature:Creature ) = POINTS;
	override def toString() = "pill"
}

class PowerPill extends Item{
	val POINTS = 50
	def doAction(creature:Creature):Int ={
  		creature.setInVulnerable()
		return POINTS
	}
	override def toString() = "PP"
}

class Cherry extends Item{
	val POINTS = 100
	def doAction(creature:Creature):Int ={
		return POINTS
	}
	override def toString() = "Ch"
}
