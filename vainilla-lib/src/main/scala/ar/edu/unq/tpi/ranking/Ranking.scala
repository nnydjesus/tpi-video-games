package ar.edu.unq.tpi.ranking
import java.io.ObjectInputStream
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.resource.TraitResources
import java.io.ObjectOutputStream
import Ordering.Implicits._

class Ranking {
  
  var ranking:Buffer[PlayersRanking] = null
   
  def addPlayer(player:PlayersRanking) = {
    if(this.ranking == null){
      this.ranking = Buffer()
    }
    ranking.append(player)
    ranking = ranking.sorted
    if(ranking.length > 10){
       ranking = ranking.-(ranking.last)
    }
  }

}

class PlayersRanking(var name:String, var score:Double) extends Ordered[PlayersRanking]{
  def compare(that: PlayersRanking) = that.score.compare(score)

}

object Ranking extends TraitResources{
  
  val RANKING_FILE_NAME = "/ranking.dat"
  
  def getRanking():Ranking = {
    var file = getFileInputStream(RANKING_FILE_NAME)
    var objectInput = new ObjectInputStream(file)
    return objectInput.readObject().asInstanceOf[Ranking]
  }
  
  def saveRanking(ranking:Ranking)= {
    var file = new ObjectOutputStream(getFileOutputStream(RANKING_FILE_NAME))
    file.writeObject(ranking)
    file.close()
  }
  
  def generateFakePlayers(){
    var ranking = new Ranking()
    ranking.addPlayer(new PlayersRanking("aaaa", 220))
    ranking.addPlayer(new PlayersRanking("bbb", 315))
    ranking.addPlayer(new PlayersRanking("ccc", 218))
    ranking.addPlayer(new PlayersRanking("ddd", 5))
    ranking.addPlayer(new PlayersRanking("eee", 1))
    ranking.addPlayer(new PlayersRanking("fff", 30))
    ranking.addPlayer(new PlayersRanking("ggg", 3))
    ranking.addPlayer(new PlayersRanking("hhh", 5))
    ranking.addPlayer(new PlayersRanking("iii", 9))
    this.saveRanking(ranking)
  }
  
  def main(args:Array[String]){
//    Ranking.saveRanking(new Ranking())
    Ranking.generateFakePlayers()
//    var a = Ranking.getRanking()
//    a.ranking
  }
}