package ar.unq.tpi.nny.pacman.domain

package ar.unq.tpi.nny.pacman.domain;

import scala.collection.mutable.Buffer

trait TraversableBlock extends Block {

	val items = Buffer[Item]()
	val  creatures = Buffer[Creature]()


	def addItem(item:Item )= items.append(item);
	def removeItem(item:Item) = items.-=(item)

	def addCreature(creature:Creature) = creatures.append(creature);
	def removeCreature(creature:Creature) = creatures.-=(creature)

	def isTraversable() =  true

	def containsItem() = items.size != 0;

	def containsPacman():Boolean =  {
	  creatures.foreach(creature => {
			if (creature.isControlledByPlayer()) {
				return true;
			}
		})

		return false;
	}
	
	def getPacman():Pacman=  {
	  creatures.foreach(creature => {
			if (creature.isControlledByPlayer()) {
				creature match{
				  case p:Pacman => return p
				};
			}
		})

		return null;
	}


	def containsAiControlledCreature():Boolean = {
	  creatures.foreach(creature => {
			if (!creature.isControlledByPlayer()) {
				return true;
			}
		})

		return false;
	}

	def collisionHappened():Boolean = {
		return containsAiControlledCreature() && containsPacman();
	}

	override def toString():String ={
		if (creatures.size != 0) {
			return creatures(0).toString();
		} else if (items.size != 0) {
			return items(0).toString();
		} else {
			return " ";
		}
	}
}
