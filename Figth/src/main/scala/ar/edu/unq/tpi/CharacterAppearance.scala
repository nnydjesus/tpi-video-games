package ar.edu.unq.tpi
import scala.collection.mutable.HashMap

import com.uqbar.vainilla.appearances.Sprite

import ar.edu.unq.tpi.resource.TraitResources

abstract class CharacterAppearance(any: Any) extends TraitResources {

  def this() {
    this(null)
    configureBaseAppearance
  }
  
  var name: String = _

  def configureBaseAppearance()
  def configureAppearance()

  val movements = new HashMap[Movement, FightMoves]()
  var selectedImage: Sprite = null
  var vsImage: Sprite = null

  def addMove(move: Movement, fight: FightMoves) {
    this.movements(move) = fight
  }

  def getMove(move: Movement) = this.movements(move)

  def selectedAnimation(orientation:Orientation.Orientation) = getMove(IDLE).getAnimation(orientation)
}

object Ragna extends CharacterAppearance {

  def configureBaseAppearance() {
    this.selectedImage = sprite("ragna/ragnaSelected.png")
    this.vsImage = sprite("ragna/ragna-vs.png")
    var ragnaWalk = sprite("ragna/walk.png")
    var ragnaWalkXML = xmlFromFile("img/ragna/walk.xml")
    this.addMove(IDLE, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))
    this.addMove(WALK, new FightMoves("wl", ragnaWalk, ragnaWalkXML, 1))
    this.addMove(WALK_BACK, new FightMoves("wr", ragnaWalk, ragnaWalkXML, 1))
    this.addMove(JUMP, new FightMoves("rg024_", ragnaWalk, ragnaWalkXML, 0, 2))
    this.name = "Ragna"
  }

  def configureAppearance {

    var meantimeAnimation = 0.001

    var ragnaCombos = sprite("ragna/combos.png")
    var ragnaCombosXML = xmlFromFile("img/ragna/combos.xml")

    var ragnaGolpe1 = sprite("ragna/golpe1.png")
    var ragnaGolpe1XML = xmlFromFile("img/ragna/golpe1.xml")

    var ragnaGolpe2 = sprite("ragna/golpe2.png")
    var ragnaGolpe2XML = xmlFromFile("img/ragna/golpe2.xml")
    
    var ragnaKicked = sprite("ragna/damage.png")
    var ragnaKickedXML = xmlFromFile("img/ragna/damage.xml")

    var ragnaPatadas1 = sprite("ragna/patada1.png")
    var ragnaPatadas1XML = xmlFromFile("img/ragna/patada1.xml")
    var ragnaPatadas2 = sprite("ragna/patada2.png")
    var ragnaPatadas2XML = xmlFromFile("img/ragna/patada2.xml")
    var ragnaPatadas3 = sprite("ragna/patada3.png")
    var ragnaPatadas3XML = xmlFromFile("img/ragna/patada3.xml")
    var ragnaPatadas4 = sprite("ragna/patada4.png")
    var ragnaPatadas4XML = xmlFromFile("img/ragna/patada4.xml")

    this.addMove(HIGH_KICK1, new FightMoves("rg201_", ragnaPatadas1, ragnaPatadas1XML, 10))
    this.addMove(HIGH_KICK2, new FightMoves("rg211_", ragnaPatadas2, ragnaPatadas2XML, 50))
    this.addMove(LOW_KICK1, new FightMoves("rg231_", ragnaPatadas3, ragnaPatadas3XML, 10))
    this.addMove(LOW_KICK2, new FightMoves("rg312_", ragnaPatadas4, ragnaPatadas4XML, 20))

    this.addMove(HIGH_PUCH1, new FightMoves("rg203_", ragnaGolpe1, ragnaGolpe1XML, 10))
    this.addMove(HIGH_PUCH2, new FightMoves("rg203_", ragnaGolpe1, ragnaGolpe1XML, 10))

    this.addMove(LOW_PUNCH2, new FightMoves("rg230_", ragnaGolpe2, ragnaGolpe2XML, 10))
    this.addMove(LOW_PUNCH1, new FightMoves("rg230_", ragnaGolpe2, ragnaGolpe2XML, 10))

    this.addMove(COMBO1, new FightMoves("rg212_", ragnaCombos, ragnaCombosXML, 10))
    this.addMove(KICKED, new FightMoves("rg050_", ragnaKicked, ragnaKickedXML, 0, 0.05))

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
    
        //    var ragnaEspecials = sprite("ragna/ragnaEspecials.png"))
    //    var ragnaEspecialsXML = xmlFromFile("img/ragna/ragnaEspecials.xml").getFile())

    //  this.addMove(IDLE, new IdleMoves("rg000_", ragnaWalk, ragnaWalkXML, 0, 0.05))

    //
    //

  }

}

object Litchi extends CharacterAppearance {

  def configureBaseAppearance() {
    this.selectedImage = sprite("litchi/litchiSelected.png")
    this.vsImage = sprite("litchi/litchi-vs.png")
    var litchiWait = sprite("litchi/litchiwait.png")
    var litchiWaitXML = xmlFromFile("img/litchi/litchiwait.xml")
    this.addMove(IDLE, new FightMoves("lc000_", litchiWait, litchiWaitXML, 0, 0.05))
    this.name = "Litchi"
  }
  
  def configureAppearance {

    var meantimeAnimation = 0.001

    var litchiWalk = sprite("litchi/walk-front.png")
    var litchiWalkXML = xmlFromFile("img/litchi/walk-front.xml")
    
    var litchiWalkBack = sprite("litchi/walk-back.png")
    var litchiWalkBackXML = xmlFromFile("img/litchi/walk-back.xml")

    var litchiGolpes = sprite("litchi/litchigolpes.png")
    var litchiGolpesXML = xmlFromFile("img/litchi/litchigolpes.xml")

    var litchiPatadas = sprite("litchi/litchipatadas.png")
    var litchiPatadasXML = xmlFromFile("img/litchi/litchipatadas.xml")

    this.addMove(JUMP, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 0, 0.05))

    this.addMove(WALK, new FightMoves("lc030_", litchiWalk, litchiWalkXML, 1))
    this.addMove(WALK_BACK, new FightMoves("lc031_", litchiWalkBack, litchiWalkBackXML, 1))

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

