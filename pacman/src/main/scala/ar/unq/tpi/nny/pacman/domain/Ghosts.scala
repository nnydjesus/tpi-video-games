package ar.unq.tpi.nny.pacman.domain

class Blinky(startPosition:Position, currentDirection:Direction ) extends Ghost(startPosition, currentDirection, new BlinkyController()) {
  this.name = "blinky"
}

class Pinky(startPosition:Position, currentDirection:Direction ) extends Ghost(startPosition, currentDirection, new PinkyController()) {
  this.name = "pinky"
}

class Clyde(startPosition:Position, currentDirection:Direction ) extends Ghost(startPosition, currentDirection, new ClydeController()) {
  this.name = "clyde"
}

class Inky(startPosition:Position, currentDirection:Direction ) extends Ghost(startPosition, currentDirection, new InkyController()) {
  this.name = "inky"
}
