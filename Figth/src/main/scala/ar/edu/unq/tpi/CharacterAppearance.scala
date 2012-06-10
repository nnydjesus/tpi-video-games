package ar.edu.unq.tpi
import scala.collection.mutable.HashMap

import com.uqbar.vainilla.appearances.Sprite

import ar.edu.unq.tpi.resource.TraitResources

abstract class CharacterAppearance(any: Any) extends TraitResources {

  def this() {
    this(null)
    this.configureAppearance()
  }

  var name: String = _

  def configureAppearance()

  val movements = new HashMap[Movement, FightMoves]()
  var selectedImage: Sprite = null

  def addMove(move: Movement, fight: FightMoves) {
    this.movements(move) = fight
  }

  def getMove(move: Movement) = this.movements(move)

  def selectedAnimation = getMove(IDLE).getAnimation(Orientation.LEFT)
}

object Ragna extends CharacterAppearance {

  def configureAppearance {

    this.name = "Ragna"

    var meantimeAnimation = 0.001

    var ragnaWalk = sprite("ragna/walk.png")
    var ragnaWalkXML = xmlFromFile("img/ragna/walk.xml")

    var ragnaCombos = sprite("ragna/combos.png")
    var ragnaCombosXML = xmlFromFile("img/ragna/combos.xml")

    var ragnaGolpe1 = sprite("ragna/golpe1.png")
    var ragnaGolpe1XML = xmlFromFile("img/ragna/golpe1.xml")

    var ragnaGolpe2 = sprite("ragna/golpe2.png")
    var ragnaGolpe2XML = xmlFromFile("img/ragna/golpe2.xml")

    var ragnaPatadas1 = sprite("ragna/patada1.png")
    var ragnaPatadas1XML = xmlFromFile("img/ragna/patada1.xml")
    var ragnaPatadas2 = sprite("ragna/patada2.png")
    var ragnaPatadas2XML = xmlFromFile("img/ragna/patada2.xml")
    var ragnaPatadas3 = sprite("ragna/patada3.png")
    var ragnaPatadas3XML = xmlFromFile("img/ragna/patada3.xml")
    var ragnaPatadas4 = sprite("ragna/patada4.png")
    var ragnaPatadas4XML = xmlFromFile("img/ragna/patada4.xml")

    //    var ragnaEspecials = sprite("ragna/ragnaEspecials.png"))
    //    var ragnaEspecialsXML = xmlFromFile("img/ragna/ragnaEspecials.xml").getFile())

    this.addMove(IDLE, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))

    this.addMove(WALK, new FightMoves("wl", ragnaWalk, ragnaWalkXML, 0))
    this.addMove(WALK_BACK, new FightMoves("wr", ragnaWalk, ragnaWalkXML, 0))
    this.addMove(JUMP, new FightMoves("rg024_", ragnaWalk, ragnaWalkXML, 0, 2))
    //
    this.selectedImage = sprite("ragna/ragnaSelected.png")
    //
    this.addMove(HIGH_KICK1, new FightMoves("rg201_", ragnaPatadas1, ragnaPatadas1XML, 10))
    this.addMove(HIGH_KICK2, new FightMoves("rg211_", ragnaPatadas2, ragnaPatadas2XML, 50))
    this.addMove(LOW_KICK1, new FightMoves("rg231_", ragnaPatadas3, ragnaPatadas3XML, 10))
    this.addMove(LOW_KICK2, new FightMoves("rg312_", ragnaPatadas4, ragnaPatadas4XML, 20))

    this.addMove(HIGH_PUCH1, new FightMoves("rg203_", ragnaGolpe1, ragnaGolpe1XML, 10))
    this.addMove(HIGH_PUCH2, new FightMoves("rg203_", ragnaGolpe1, ragnaGolpe1XML, 10))

    this.addMove(LOW_PUNCH2, new FightMoves("rg230_", ragnaGolpe2, ragnaGolpe2XML, 10))
    this.addMove(LOW_PUNCH1, new FightMoves("rg230_", ragnaGolpe2, ragnaGolpe2XML, 10))

    this.addMove(COMBO1, new FightMoves("rg212_", ragnaCombos, ragnaCombosXML, 10))
    this.addMove(KICKED, new FightMoves("rg024_", ragnaWalk, ragnaWalkXML, 0, 0.05))

    //FAKE    
    //  this.addMove(HIGH_KICK1, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(HIGH_KICK2, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(LOW_KICK1, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(LOW_KICK2, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(HIGH_PUCH1, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(HIGH_PUCH2, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(COMBO1, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(LOW_PUNCH2, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(LOW_PUNCH1, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //  this.addMove(IDLE, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    //
  }

}

object Litchi extends CharacterAppearance {

  def configureAppearance {

    this.name = "Litchi"

    var meantimeAnimation = 0.001

    var litchiWalk = sprite("litchi/litchiwalk.png")
    var litchiWalkXML = xmlFromFile("img/litchi/litchiwalk.xml")

    var litchiWait = sprite("litchi/litchiwait.png")
    var litchiWaitXML = xmlFromFile("img/litchi/litchiwait.xml")

    var litchiGolpes = sprite("litchi/litchigolpes.png")
    var litchiGolpesXML = xmlFromFile("img/litchi/litchigolpes.xml")

    var litchiPatadas = sprite("litchi/litchipatadas.png")
    var litchiPatadasXML = xmlFromFile("img/litchi/litchipatadas.xml")

    /*var ragnaEspecials = sprite("ragna/ragnaEspecials.png"))
      var ragnaEspecialsXML = xmlFromFile("img/ragna/ragnaEspecials.xml").getFile())
  */

    this.selectedImage = sprite("litchi/litchiSelected.png")
    //  this.addMove(IDLE, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    this.addMove(JUMP, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))

    this.addMove(WALK, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0))
    this.addMove(WALK_BACK, new FightMoves("lc031_", litchiWalk, litchiWalkXML, 0))

    //  this.selectedImage = sprite("litchi/litchiSelected.png")
    this.addMove(IDLE, new FightMoves("lc000_", litchiWait, litchiWaitXML, 0, 0.05))
    //    this.addMove(JUMP, new FightMoves("lc000_", litchiWait, litchiWaitXML, 0, 0.05))

    this.addMove(WALK, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0))
    this.addMove(WALK_BACK, new FightMoves("lc031_", litchiWalk, litchiWalkXML, 0))

    this.addMove(HIGH_KICK1, new FightMoves("lc211_", litchiPatadas, litchiPatadasXML, 10))
    this.addMove(HIGH_KICK2, new FightMoves("lc212_", litchiPatadas, litchiPatadasXML, 20))
    this.addMove(LOW_KICK1, new FightMoves("lc216_", litchiPatadas, litchiPatadasXML, 10))
    this.addMove(LOW_KICK2, new FightMoves("lc311_", litchiPatadas, litchiPatadasXML, 20))

    this.addMove(HIGH_PUCH1, new FightMoves("lc210_", litchiGolpes, litchiGolpesXML, 10))
    this.addMove(HIGH_PUCH2, new FightMoves("lc210_", litchiGolpes, litchiGolpesXML, 10))
    this.addMove(LOW_PUNCH2, new FightMoves("lc232_", litchiGolpes, litchiGolpesXML, 10))
    this.addMove(LOW_PUNCH1, new FightMoves("lc312_", litchiGolpes, litchiGolpesXML, 50))

    this.addMove(KICKED, new FightMoves("lc211_", litchiPatadas, litchiPatadasXML, 0.5))

    //FAKE
    //  this.addMove(HIGH_KICK1, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(HIGH_KICK2, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(LOW_KICK1, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(LOW_KICK2, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(HIGH_PUCH1, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(HIGH_PUCH2, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(LOW_PUNCH2, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))
    //  this.addMove(LOW_PUNCH1, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))

  }
}

