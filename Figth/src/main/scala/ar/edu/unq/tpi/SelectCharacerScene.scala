package ar.edu.unq.tpi

import scala.collection.mutable.Buffer
import scala.util.Random
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Sprite
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameComponent
import ar.unq.tpi.components.AnimationComponent
import ar.unq.tpi.components.ScaleSpriteComponent
import ar.unq.tpi.components.SelectScene
import ar.unq.tpi.components.SpriteComponent
import com.uqbar.vainilla.Game

class SelectCharacterScene(game: Fight) extends SelectScene() with EventGameComponent[SelectCharacterScene] {

  var player1: Player = null
  var player2: Player = null
  var stage : StageComponent = null
  var buttonLeft: ButtonStage = null
  var buttonRight: ButtonStage = null
  var stages: Array[Sprite] = new Array[Sprite](4)
  var cursorFirstPlayer: Cabezal = null
  var cursorSecondPlayer: Cabezal = null
  var animationCabezalFirst: Animation = null
  var animationCabezalSecond: Animation = null
  var backgrondAnimation: Animation = null
  var nameCharacterFirst : SpriteComponent[SelectCharacterScene] = null
  var nameCharacterSecond : SpriteComponent[SelectCharacterScene] = null
  var selectCharacterFirst: SelectableCharacter = null
  var selectCharacterSecond: SelectableCharacter = null
  var matrixComponent: SpriteComponent[SelectCharacterScene] = null
  var characters: Buffer[CharacterAppearance] = Buffer[CharacterAppearance]()
  var matrixCharacter: MatrixSelectedCharacter = new MatrixSelectedCharacter(GameValues.WIDTH_MATRIX, GameValues.HEIGHT_MATRIX, this)
  var animationSelectedFirstCharacter = new AnimationComponent(new Animation(0, 0), -150, 200)
  var animationSelectedSecondCharacter = new AnimationComponent(new Animation(0, 0), 1000, 200)
  var countSelectedCharacterFinish = 0
  var countIndexStage = 0

  override def setGame(aGame: Game) {
    super.setGame(aGame)
    player1 = game.player1
    player2 = game.player2
    createAnimationBackground()
    createStageSelected()
    createAnimationSelectedCharacters()
    createNamesOfCharacters()
    createMatrixCharacter()
    createCabezales()
    createNamesOfCharacters()
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

  def createStageSelected() {
    for (i <- 1 until 5) {
      stages(i - 1) = sprite("stage" + i + ".png")
    }
  }
  
  def createNamesOfCharacters(){
    var nameFirst = sprite("nameCharacterFirst.png")
    var nameSecond = sprite("nameCharacterSecond.png")
    nameCharacterFirst = new SpriteComponent(sprite("nameCharacterFirst.png"), 30,game.getDisplayHeight() - nameFirst.getHeight()) 
    nameCharacterSecond = new SpriteComponent(sprite("nameCharacterSecond.png"),game.getDisplayWidth() - nameSecond.getWidth(),game.getDisplayHeight() - nameSecond.getHeight()) 
    this.addComponent(nameCharacterFirst)
    this.addComponent(nameCharacterSecond)
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
    this.addComponent(new SpriteComponent(ScaleSpriteComponent.scale(sprite("backgroundSelectCharacterScene.png"), this.game.getDisplayWidth(), this.game.getDisplayHeight()), 0, 0))
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
    animationCabezalFirst = new Animation(1 / 10D, cabezalFirst)
    animationCabezalSecond = new Animation(1 / 10D, cabezalSecond)
  }

  def createAnimationSelectedCharacters() {
    this.addComponent(animationSelectedFirstCharacter)
    this.addComponent(animationSelectedSecondCharacter)
  }

  def addEventListenerCabezales() {
    cursorFirstPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedFirstCharacter.setAppearance(e.data.character.selectedAnimation(Orientation.RIGHT))
    })
    cursorFirstPlayer.addEventListener(GameEvents.CABEZAL_SELECTED, (e: Event[Cabezal, SelectableCharacter]) => {
      incrementCount()
      selectCharacterFirst = e.data
      checkSelection()
    })
    cursorFirstPlayer.addEventListener(GameEvents.BACK_SELECT_CHARACTER, (e: Event[Cabezal, Any]) => { decrementCount() })

    cursorSecondPlayer.addEventListener(GameEvents.CABEZAL_MOVIDO, (e: Event[Cabezal, SelectableCharacter]) => {
      animationSelectedSecondCharacter.setAppearance(e.data.character.selectedAnimation(Orientation.LEFT))
    })
    cursorSecondPlayer.addEventListener(GameEvents.CABEZAL_SELECTED, (e: Event[Cabezal, SelectableCharacter]) => {
      incrementCount()
      selectCharacterSecond = e.data
      checkSelection()
    })

    cursorSecondPlayer.addEventListener(GameEvents.BACK_SELECT_CHARACTER, (e: Event[Cabezal, Any]) => { decrementCount() })
  }

  def checkSelection() {
    if (countSelectedCharacterFinish == 2) {
      dispatchEvent(new Event(GameEvents.ALL_PLAYER_SELECT_CHARACTER, this, (selectCharacterFirst, selectCharacterSecond)))
    }
  }

  def incrementCount() = countSelectedCharacterFinish += 1
  def decrementCount() = countSelectedCharacterFinish -= 1

  def selectArena() {
    this.ocultarScene()

    game.selectArena(selectCharacterFirst, selectCharacterSecond)
  }

  def createStageComponent() {
    var button  = sprite("buttonLeft.png")
    var button2 = sprite("buttonRight.png")
    stage = new StageComponent(stages(0), game.getDisplayWidth(), game.getDisplayHeight())
    buttonLeft = new ButtonStage(sprite("buttonLeft.png"),stage.getX() - button.getWidth() ,stage.getY() - button.getHeight() / 2)
    buttonRight = new ButtonStage(sprite("buttonRight.png"), stage.getX() + stage.width + button2.getWidth() / 2 ,stage.getY() - button.getHeight() / 2)
    addListenerStage()
    
  }

  def ocultarScene() {

  }

  def addListenerStage() {
    stage.addEventListener(GameEvents.CHANGE_NEXT_STAGE, (e: Event[StageComponent, Any]) => {
    	buttonLeft.setAppearance(sprite("imagen.png"))
      if (stages.length > countIndexStage) {
        incrementIndex()
        e.target.setAppearance(stages(countIndexStage))
      } else {
        countIndexStage = 0
        e.target.setAppearance(stages(countIndexStage))
      }
    })
    stage.addEventListener(GameEvents.CHANGE_PREVIOUS_STAGE, (e: Event[StageComponent, Any]) => {
    	buttonRight.setAppearance(sprite("imagen.png"))
      if (countIndexStage == 0) {
        countIndexStage = (stages.length - 1)
        e.target.setAppearance(stages(countIndexStage))
      } else {
        decrementCount()
        e.target.setAppearance(stages(countIndexStage))
      }
    })
    stage.addEventListener(GameEvents.ACCEPT_STAGE, (e: Event[StageComponent, Sprite]) => {
    	//game.playGame( selectCharacterFirst , selectCharacterSecond, e.data)
    })
  }

  def incrementIndex() {
    if (countIndexStage >= 0) {
      countIndexStage += 1
    }
  }
  def decrementIndex() {
    if ((countIndexStage > 0)) {
      countIndexStage -= 1
    }
  }
  def addListener() {
    this.addEventListener(GameEvents.ALL_PLAYER_SELECT_CHARACTER, (e: Event[SelectCharacterScene, (SelectableCharacter, SelectCharacterScene)]) => {
      this.selectArena()
    })
  }
}