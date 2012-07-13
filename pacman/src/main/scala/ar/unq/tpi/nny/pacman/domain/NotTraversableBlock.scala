package ar.unq.tpi.nny.pacman.domain

trait NotTraversableBlock extends Block {

	def isTraversable()= false

	def containsItem() = false
}
