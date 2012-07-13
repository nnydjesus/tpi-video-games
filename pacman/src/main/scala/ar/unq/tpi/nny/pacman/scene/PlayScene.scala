package ar.unq.tpi.nny.pacman.scene
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import com.uqbar.vainilla.appearances.Rectangle
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameScene
import ar.edu.unq.tpi.traits.FunctionEvent
import ar.unq.tpi.components.Stats
import ar.unq.tpi.nny.pacman.component.PacmanComponent
import ar.unq.tpi.nny.pacman.component.TileMap
import ar.unq.tpi.nny.pacman.domain.Blinky
import ar.unq.tpi.nny.pacman.domain.Block
import ar.unq.tpi.nny.pacman.domain.Board
import ar.unq.tpi.nny.pacman.domain.Clyde
import ar.unq.tpi.nny.pacman.domain.Direction
import ar.unq.tpi.nny.pacman.domain.Floor
import ar.unq.tpi.nny.pacman.domain.Inky
import ar.unq.tpi.nny.pacman.domain.Pinky
import ar.unq.tpi.nny.pacman.domain.Position
import ar.unq.tpi.nny.pacman.domain.Wall
import ar.unq.tpi.nny.pacman.map.GhostComponent
import ar.unq.tpi.nny.pacman.map.MapGenerator
import ar.unq.tpi.nny.pacman.util.GameConstants
import ar.unq.tpi.nny.pacman.util.GameEvents
import ar.unq.tpi.nny.pacman.domain.Matrix
import ar.unq.tpi.nny.pacman.domain.Creature
import ar.unq.tpi.nny.pacman.component.ItemComponent
import ar.unq.tpi.nny.pacman.domain.PowerPill
import ar.unq.tpi.nny.pacman.component.PillComponent
import ar.unq.tpi.nny.pacman.component.PowerPillComponent
import ar.unq.tpi.nny.pacman.domain.Pill
import ar.unq.tpi.nny.pacman.domain.SimpleWall
import ar.unq.tpi.nny.pacman.domain.Ghost
import ar.unq.tpi.components.SpriteComponent
import ar.unq.tpi.nny.pacman.util.GameImages
import ar.unq.tpi.nny.pacman.component.CherryComponent
import ar.unq.tpi.components.DynamicLabelComponent
import ar.unq.tpi.nny.pacman.util.Fonts

class PlayScene extends GameScene with TraitResources with EventGameScene with EventGameComponent[PlayScene] {

  var mapGenerator = new MapGenerator(this)
  var pacman: PacmanComponent = _
  var creatures = Buffer[Creature]()
  var bloks = new Matrix[Block](28, 36)
  var board: Board = _
  var remaining = 0D
  var score = 0
  val ready = getSound("Ready.wav")

  var cherry: CherryComponent = _

  override def setGame(game: Game) {
    ready.play()
    super.setGame(game)
    var background = new GameComponent(new Rectangle(Color.BLACK, game.getDisplayWidth(), game.getDisplayHeight()), 0, 0)
    background.setZ(-10)
    this.addComponent(background)
    mapGenerator.generateByImage(GameConstants.MAP_DENSITY_NAME)
    createBoard()
    createCherry()
    val tileMap = new TileMap(bloks, 0, 0)
    this.addComponent(tileMap)
    this.addComponent(new Stats(new Font(Font.SANS_SERIF, Font.BOLD, 25), Color.BLUE, tileMap.width +20, 0))
    this.addComponent(new DynamicLabelComponent(()=>"Score: " + score, Fonts.CRACKMAN.deriveFont(45f), Color.WHITE,  100, tileMap.height -80))
    creatures.foreach(c => c.directionToPosition = board.directionToPosition)
  }

  def createCherry() {
    cherry = new CherryComponent(null, board.CHERRY_POSITION.row , board.CHERRY_POSITION.column )
    cherry.addEventListener(GameEvents.REMOVE_CHERRY, (e: Event[ItemComponent, Any]) => 
      this.removeComponent(cherry))
  }

  def createBoard() {
    board = new Board(bloks, creatures, pacman.pacmam)
    board.addEventListener(GameEvents.PLACE_CHERRY, (e: Event[Board, Any]) => this.addComponent(cherry))
    board.addEventListener(GameEvents.REMOVE_CHERRY, (e: Event[Board, Any]) => this.removeComponent(cherry))

  }

  def updateScore() {
    score += pacman.pacmam.handlePoints();
  }

  override def takeStep(graphics: Graphics2D, state: DeltaState) {
    super.takeStep(graphics, state)
    if (remaining <= 0) {
      updateScore()
      board.step()
      remaining = 0.5
    }
    remaining -= state.getDelta()
  }

  def addFlor(x: Int, y: Int) {
    this.bloks.put(x, y, new Floor())

  }

  def addWall(x: Int, y: Int) {
    this.bloks.put(x, y, new Wall())
  }

  def addSimpleWall(x: Int, y: Int) {
    this.bloks.put(x, y, new SimpleWall())
  }

  def addStar(x: Int, y: Int) {
    var pill = new Pill()
    val star = new PillComponent(pill, x, y)
    star.addEventListener(GameEvents.COLLIDE_PACMAN_WITH_STAR, new FunctionEvent(onCollideStar))
    this.addComponent(star)
    this.bloks.put(x, y, new Floor(pill))
  }

  def addHeart(x: Int, y: Int) {
    var powerPill = new PowerPill()
    val heart = new PowerPillComponent(powerPill, x, y)
    heart.addEventListener(GameEvents.COLLIDE_PACMAN_WITH_HEART, new FunctionEvent(onCollideHeart))
    this.addComponent(heart)
    this.bloks.put(x, y, new Floor(powerPill))
  }

  def addPacman(x: Int, y: Int) {
    this.pacman = new PacmanComponent(x * GameConstants.TILE, y * GameConstants.TILE)
    creatures.append(this.pacman.pacmam)
    this.pacman.pacmam.timerIsRunningOut = () => this.board.updateAiControlledCreaturesStatus()
    this.addComponent(pacman)
    this.bloks.put(x, y, new Floor())
  }

  def addPinky(x: Int, y: Int) {
    val pinky = new GhostComponent(new Pinky(new Position(x, y), Direction.DOWN), "pinky.png", x, y)
    this.addGhost(pinky, x, y)
  }
  def addInky(x: Int, y: Int) {
    val inky = new GhostComponent(new Inky(new Position(x, y), Direction.DOWN), "inky.png", x, y)
    this.addGhost(inky, x, y)
  }

  def addClyde(x: Int, y: Int) {
    val clyde = new GhostComponent(new Clyde(new Position(x, y), Direction.DOWN), "clyde.png", x, y)
    this.addGhost(clyde, x, y)
  }

  def addBlinky(x: Int, y: Int) {
    val blinky = new GhostComponent(new Blinky(new Position(x, y), Direction.DOWN), "blinky.png", x, y)
    this.addGhost(blinky, x, y)
  }

  def addGhost(ghost: GhostComponent, x: Int, y: Int) {
    this.creatures.append(ghost.ghost)
    this.addComponent(ghost)
    this.bloks.put(x, y, new Floor())

    ghost.addEventListener(GameEvents.COLLIDE_PACMAN_WITH_GHOST, eatPacman)
  }

  def eatPacman: Event[GhostComponent, Any] => Unit = event => {
    this.pacman.eat(event.target)
  }

  def onCollideStar(event: Event[ItemComponent, Any]) {
    event.target.item.doAction(pacman.pacmam)
  }

  def onCollideHeart(event: Event[ItemComponent, Any]) {
    event.target.item.doAction(pacman.pacmam)
  }

}