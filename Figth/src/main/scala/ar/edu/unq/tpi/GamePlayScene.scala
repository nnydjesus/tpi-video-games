package ar.edu.unq.tpi

import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.Game
import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.GameScene

import ar.edu.unq.tpi.resource.TraitResources
import ar.edu.unq.tpi.traits.Event
import ar.edu.unq.tpi.traits.EventGameScene
import ar.edu.unq.tpi.traits.Function
import ar.unq.tpi.components.AnimateSprite
import ar.unq.tpi.components.CenterComponent
import ar.unq.tpi.components.ScrollingBackroundComponent
import ar.unq.tpi.components.ScrollingSprite
import ar.unq.tpi.components.Selectable
import ar.unq.tpi.components.Stats
import traits.EventGameComponent

class GamePlayScene(game: Fight, character: CharacterAppearance) extends GameScene with BoundsScene with EventGameScene with EventGameComponent[GamePlayScene] {

  var finish = false

  var character1 = new CharacterFight(new Player1(), new Character(character), this, 200, 800)
  var character2 = new CharacterFight(new Player2(), new Character(Litchi), this, 1200, 700)

  val winAnimate = new AnimateSprite(GameImage.WIN_IMAGE)
  val loseAnimate = new AnimateSprite(GameImage.LOSE_IMAGE)

  var finishAnimation = new CenterComponent(winAnimate, game.getDisplayWidth(), game.getDisplayHeight())
  var state: StateRaund = FirstRaund
  var countVictorysChF: Int = 0
  var countVictorysChS: Int = 0
  var backGround: ScrollingBackroundComponent[GameScene] = null
  
  val ON_LEFT_MAP_MOVE = new Function(onLeftMapMove)
  val ON_RIGTH_MAP_MOVE = new Function(onRigthMapMove)
  val ON_DEATH_1 = new Function(onDeath1)
  val ON_DEATH_2 = new Function(onDeath2)

  def this(game: Fight, character: CharacterAppearance, arena: Selectable) {
    this(game, character)

    character1.oponent = character2
    character2.oponent = character1

    var backGroundSprite = arena.image.scale((2 * game.getDisplayWidth()) / arena.image.getWidth(), game.getDisplayHeight() / arena.image.getHeight())
    backGround = new ScrollingBackroundComponent[GameScene](new ScrollingSprite(backGroundSprite.getImage(), game.getDisplayWidth()), 0, 0)

    this.addComponent(new Stats(0, 0))

    this.addComponents(backGround)
    this.addComponents(character1, character2, new LifeBar(GameImage.LIFE_BAR, 100, 100, character1.character.getLife), new LifeBar(GameImage.LIFE_BAR, 1000, 100, character2.character.getLife))
  }

  def startRound(state: StateRaund) {
    this.state = state
    character1.character.life = GameValues.PLAYER_LIFE
    character2.character.life = GameValues.PLAYER_LIFE

    character1.character.death = false
    character2.character.death = false

    character1.setX(200)
    character1.setY(800)
    character2.setX(1200)
    character2.setY(700)
    
    val roundComponent = new RoundComponent(this.state.animationRound, getGame().getDisplayWidth(), getGame().getDisplayHeight(), 2)
    roundComponent.addEventListener(GameEvents.FINISH_ANIMATION, new Function(onStart))
    
    this.addComponent(roundComponent)
    
  }
  
  def onStart(event:Event[RoundComponent, Any]){
	  this.removeComponent(event.target)
	  configureListeners()
    
  }


  def configureListeners() {
    character1.addEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, ON_LEFT_MAP_MOVE)
    character1.addEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, ON_RIGTH_MAP_MOVE)
    character2.addEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, ON_LEFT_MAP_MOVE)
    character2.addEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, ON_RIGTH_MAP_MOVE)

    character1.addEventListener(GameEvents.DEATH, ON_DEATH_1)
    character2.addEventListener(GameEvents.DEATH, ON_DEATH_2)
  }

  def removeListeners() {
    character1.removeEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, ON_LEFT_MAP_MOVE)
    character1.removeEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, ON_RIGTH_MAP_MOVE)
    character2.removeEventListener(GameEvents.COLLIDE_WITH_BOUND_LEFT, ON_LEFT_MAP_MOVE)
    character2.removeEventListener(GameEvents.COLLIDE_WITH_BOUND_RIGTH, ON_RIGTH_MAP_MOVE)

    character1.removeEventListener(GameEvents.DEATH, ON_DEATH_1)
    character2.removeEventListener(GameEvents.DEATH, ON_DEATH_2)
  }

  def onDeath1(event: Event[CharacterFight, Any]) {
    this.countVictorysChF += 1
    onDeath(loseAnimate)
  }

  def onDeath2(event: Event[CharacterFight, Any]) {
    this.countVictorysChS += 1
    onDeath(winAnimate)
  }

  def onDeath(finishAnimationAppearance: AnimateSprite) {
    removeListeners()
    finishAnimation.setAppearance(finishAnimationAppearance)
    this.addComponent(finishAnimation)

    new Thread() {
      override def run() {
        Thread.sleep(5 * 1000)
        GamePlayScene.this.removeComponent(finishAnimation)
        state.stepNext(GamePlayScene.this)
      }
    }.start()
  }

  def finishFigth() {
    dispatchEvent(new Event(GameEvents.FINISH_FIGTH, this))
  }

  def onRigthMapMove(event: Event[CharacterFight, Orientation.Orientation]) {
    backGround.sprite.avance()
  }

  def onLeftMapMove(event: Event[CharacterFight, Orientation.Orientation]) {
    backGround.sprite.retroceder()
  }

  def centerX = game.getDisplayWidth() / 2
  def centerY = game.getDisplayHeight() / 2

}