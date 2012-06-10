package ar.edu.unq.tpi.ranking

class Player(var name: String, var score: Double, var life: Int, var level:Int = 1) {
  
    
  def increaseScore(value: Double) = score += value
  def decreaseScore(value: Double) = score -= value

  def lostLife() = life -= 1
  def addLife() = life += 1
  
  def levelUp() = level += 1

}