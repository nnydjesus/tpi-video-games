package ar.edu.unq.tpi
import java.awt.Dimension
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene
import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.collide.Colicionable
import ar.unq.tpi.components.AnimationComponent
import ar.unq.tpi.components.ButtonComponent
import ar.unq.tpi.components.MouseComponent
import ar.unq.tpi.components.ScaleSpriteComponent
import ar.unq.tpi.components.SelectComponent
import ar.unq.tpi.components.SelectScene
import ar.unq.tpi.components.Selectable
import ar.unq.tpi.components.SpriteComponent
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.edu.unq.tpi.traits.Event
import scala.collection.mutable.Buffer
import java.awt.Graphics2D
import ar.edu.unq.tpi.traits.FunctionSceneListener
import scala.collection.JavaConversions._
import scala.util.Random

class LoadingScene(characterAppearance1: CharacterAppearance, characterAppearance2: CharacterAppearance) extends GameScene() with TraitResources with EventGameComponent[LoadingScene] {

  override def setGame(game: Game) {
    super.setGame(game)
    this.addComponent(new SpriteComponent(ScaleSpriteComponent.scale(GameImage.LOADING, this.getGame().getDisplayWidth(), this.getGame().getDisplayHeight()), 0, 0))
    load()
  }

  def load() {
    new Thread() {
      override def run() {
        characterAppearance1.configureAppearance();
        characterAppearance2.configureAppearance();
        dispatchEvent(new Event(GameEvents.LOAD_RESOURCE, LoadingScene.this))
      }
    }.start()
  }

}

class SelectArenaScene(game: Fight, character: CharacterAppearance) extends SelectScene() {

  val sizeBigArena = new Dimension(900, 500)
  val width = 300
  val height = 250
  val initialX = 20
  val initialY = 20
  val padding = 50
  var arenaSelected: Selectable = Arena1

  this.addComponent(new SelectComponent(this, Arena1, initialX, initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena2, initialX + (width) + padding, initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena3, initialX + (2 * width) + (2 * padding), initialY, width, height))
  this.addComponent(new SelectComponent(this, Arena4, initialX + (3 * width) + (3 * padding), initialY, width, height))

  var bigArenaComponent = new GameComponent[GameScene, Appearance](scaleBigArena(arenaSelected.image), 400, 300)
  this.addComponent(new GameComponent[GameScene, Animation](character.selectedAnimation, 10, 300))

  this.addComponent(bigArenaComponent)
  var start = GameImage.BUTTON_START

  this.addComponent(new ButtonComponent(start, start, game.getDisplayWidth() - (start.getWidth() + 100), game.getDisplayHeight() - (start.getHeight() + 100), onStart))
  this.addComponent(new MouseComponent(GameImage.SWORD, 0, 0))

  override def selectItem(arena: Selectable) {
    arenaSelected = arena
    bigArenaComponent.setAppearance(scaleBigArena(arena.image))

  }

  def onStart(delta: DeltaState) {
    game.playGame(character, arenaSelected)
  }

  def scaleBigArena(sprite: Sprite) = sprite.scale(sizeBigArena.width / sprite.getWidth(), sizeBigArena.height / sprite.getHeight())
}

class SelectCharacterScene(game: Fight) extends SelectScene() {

  var player1: Player = null
  var player2: Player = null
  var start = GameImage.BUTTON_START
  var cursorFirstPlayer: Cabezal = null
  var cursorSecondPlayer: Cabezal = null
  var backgrondAnimation: Animation = null
  var matrixComponent: SpriteComponent[SelectCharacterScene] = null
  var characters: Buffer[CharacterAppearance] = Buffer[CharacterAppearance]()
  var matrixCharacter: MatrixSelectedCharacter = new MatrixSelectedCharacter(GameValues.WIDTH_MATRIX, GameValues.HEIGHT_MATRIX, this)
  var animationSelectedFirstCharacter = new AnimationComponent(new Animation(0, 0), -150, 200)
  var animationSelectedSecondCharacter = new AnimationComponent(new Animation(0, 0), 700, 200)

  //  var 
  //var  aca voy a poner una variable para poder hacer lo de la seleccion
  
  init()

  def init() {
    player1 = new Player1()
    player2 = new Player2()
    createAnimationBackground()
    createAnimationSelectedCharacters()
    createMatrixCharacter()
    createCabezales()
  }

  def createMatrixCharacter() {
    createListCharacter(GameValues.WIDTH_MATRIX * GameValues.HEIGHT_MATRIX)
    matrixCharacter.loadSelectedCharacter(this.characters)
    matrixCharacter.paintSelectedCharacter()
    matrixComponent = new SpriteComponent(matrixCharacter.paintSelectedCharacter(), (game.getDisplayWidth() / 2) - ((GameValues.WIDTH_MATRIX * GameValues.WIDTH_SELECTED_CHARACTER) / 2), (game.getDisplayHeight() / 2) - ((GameValues.HEIGHT_MATRIX * GameValues.HEIGHT_SELECTED_CHARACTER) / 2))
    this.addComponent(matrixComponent)
  }

  def createListCharacter(size: Int) {
    var toSelectCharacter: Buffer[CharacterAppearance] = Buffer[CharacterAppearance]()
    toSelectCharacter.append(Ragna, Litchi)
    for (i <- 0 until size ) {
      characters.append(toSelectCharacter(Random.nextInt(2)))
    }
  }

  def createCabezales() {
    var cursorFirstPlayer = new Cabezal(player1, sprite("cabezal.png"), 1, 2, matrixComponent.getX(), matrixComponent.getY())
    var cursorSecondPlayer = new Cabezal(player2, sprite("cabezal.png"), 1, 3, matrixComponent.getX(), matrixComponent.getY())
    cursorFirstPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedFirstCharacter.setAppearance(e.data.character.selectedAnimation)
    })
    cursorSecondPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedSecondCharacter.setAppearance(e.data.character.selectedAnimation)
    })
    this.addComponent(cursorFirstPlayer)
    this.addComponent(cursorSecondPlayer)
  }

  def createAnimationBackground() {
//    var background = new Array[Sprite](11)
//    for (i <- 1 until 12) {
//      background(i - 1) = ScaleSpriteComponent.scale(sprite("background/" + "00" + i + ".png"), game.getDisplayWidth(), game.getDisplayHeight())
//    }
//    backgrondAnimation = new Animation(1 / 5D, background)
    this.addComponent(new SpriteComponent(sprite("background/0010.png"), 0, 0))
  }
  def createAnimationSelectedCharacters() {
    this.addComponent(animationSelectedFirstCharacter)
    this.addComponent(animationSelectedSecondCharacter)
  }

  //  def onStart(delta: DeltaState) {
  //    game.selectArena(characterFirstSelected)
  //  }
}