package ar.edu.unq.tpi

import ar.edu.unq.tpi.traits.EventGameComponent
import com.uqbar.vainilla.appearances.Animation
import ar.unq.tpi.components.SpriteComponent
import ar.unq.tpi.components.SelectScene
import ar.unq.tpi.components.AnimationComponent
import com.uqbar.vainilla.appearances.Sprite
import scala.collection.mutable.Buffer
import scala.util.Random
import ar.edu.unq.tpi.traits.Event
import com.uqbar.vainilla.events.constants.Key

class SelectCharacterScene(game: Fight) extends SelectScene() with EventGameComponent[SelectCharacterScene] {

  var player1: Player = null
  var player2: Player = null
  var start = GameImage.BUTTON_START
  var cursorFirstPlayer: Cabezal = null
  var cursorSecondPlayer: Cabezal = null
  var animationCabezalFirst: Animation = null
  var animationCabezalSecond: Animation = null
  var backgrondAnimation: Animation = null
  var selectCharacterFirst: SelectableCharacter = null
  var selectCharacterSecond: SelectableCharacter = null
  var matrixComponent: SpriteComponent[SelectCharacterScene] = null
  var characters: Buffer[CharacterAppearance] = Buffer[CharacterAppearance]()
  var matrixCharacter: MatrixSelectedCharacter = new MatrixSelectedCharacter(GameValues.WIDTH_MATRIX, GameValues.HEIGHT_MATRIX, this)
  var animationSelectedFirstCharacter = new AnimationComponent(new Animation(0, 0), -150, 200)
  var animationSelectedSecondCharacter = new AnimationComponent(new Animation(0, 0), 700, 200)
  var countSelectedCharacterFinish = 0

  init()

  def init() {
    player1 = game.player1
    player2 = game.player2
    createAnimationBackground()
    createAnimationSelectedCharacters()
    createMatrixCharacter()
    createCabezales()
    addListener()
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
    for (i <- 0 until size) {
      characters.append(toSelectCharacter(Random.nextInt(2)))
    }
  }

  def createCabezales() {
    createAnimationCabezales()
    cursorFirstPlayer = new Cabezal(player1, animationCabezalFirst, 1, 2, matrixComponent.getX(), matrixComponent.getY())
    cursorSecondPlayer = new Cabezal(player2, animationCabezalSecond, 1, 3, matrixComponent.getX(), matrixComponent.getY())
    this.addEventListenerCabezales()
    this.addComponent(cursorFirstPlayer)
    this.addComponent(cursorSecondPlayer)
  }

  def createAnimationBackground() {
    this.addComponent(new SpriteComponent(sprite("background/0010.png"), 0, 0))
  }
  def createAnimationCabezales() {
    var cabezalFirst = new Array[Sprite](4)
    var cabezalSecond = new Array[Sprite](4)
    for (i <- 1 until 5) {
      cabezalFirst(i - 1) = sprite("cabezalFirst" + i + ".png")
    }
    for (i <- 1 until 5) {
      cabezalSecond(i - 1) = sprite("cabezalSecond" + i + ".png")
    }
    animationCabezalFirst = new Animation(1 / 5D, cabezalFirst)
    animationCabezalSecond = new Animation(1 / 5D, cabezalSecond)
  }

  def createAnimationSelectedCharacters() {
    this.addComponent(animationSelectedFirstCharacter)
    this.addComponent(animationSelectedSecondCharacter)
  }
  def addEventListenerCabezales() {
    cursorFirstPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedFirstCharacter.setAppearance(e.data.character.selectedAnimation)
    })
    cursorFirstPlayer.addEventListener(GameEvents.CABEZAL_SELECTED, (e: Event[Cabezal, SelectableCharacter]) => {
      incrementCount()
      selectCharacterFirst = e.data
      checkSelection()
    })
    cursorFirstPlayer.addEventListener(GameEvents.BACK_SELECT_CHARACTER, (e: Event[Cabezal, Any]) => {
      decrementCount()
    })
    cursorSecondPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedSecondCharacter.setAppearance(e.data.character.selectedAnimation)
    })
    cursorSecondPlayer.addEventListener(GameEvents.CABEZAL_SELECTED, (e: Event[Cabezal, SelectableCharacter]) => {
      incrementCount()
      selectCharacterSecond = e.data
      checkSelection()
    })
    cursorSecondPlayer.addEventListener(GameEvents.BACK_SELECT_CHARACTER, (e: Event[Cabezal, Any]) => {
      decrementCount()
    })
  }

  def checkSelection() {
    if (countSelectedCharacterFinish == 2) {
      dispatchEvent(new Event(GameEvents.ALL_PLAYER_SELECT_CHARACTER, this, (selectCharacterFirst, selectCharacterSecond)))
    }
  }

  def incrementCount() = countSelectedCharacterFinish += 1
  def decrementCount() = countSelectedCharacterFinish -= 1
  def selectArena() = game.selectArena(selectCharacterFirst, selectCharacterSecond)

  def addListener() {
    this.addEventListener(GameEvents.ALL_PLAYER_SELECT_CHARACTER, (e: Event[SelectCharacterScene, (SelectableCharacter, SelectCharacterScene)]) => {
      this.selectArena()
    })
  }
}