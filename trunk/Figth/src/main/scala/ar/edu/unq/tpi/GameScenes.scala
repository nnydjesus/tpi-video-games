package ar.edu.unq.tpi
import java.awt.Dimension
import com.uqbar.vainilla.appearances.Animation
import com.uqbar.vainilla.appearances.Appearance
import com.uqbar.vainilla.appearances.Sprite
import com.uqbar.vainilla.events.constants.Key
import com.uqbar.vainilla.DeltaState
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
import com.uqbar.vainilla.Game
import scala.collection.mutable.Map

class InitialScene(game: Fight) extends GameScene() with TraitResources {

  this.addComponent(new GameComponent[GameScene, Appearance](sprite("fondo1.png").scale(1, 1.3), 0, 0) {
    override def update(delta: DeltaState) {
      if (delta.isKeyBeingHold(Key.ENTER)) {
        //        game.playGame()
      }
    }
  })
}

class LoadingScene(characterAppearance1: CharacterAppearance, characterAppearance2: CharacterAppearance) extends GameScene() with TraitResources {
  
  override def setGame(game:Game){
    super.setGame(game)
    this.addComponent(new SpriteComponent(ScaleSpriteComponent.scale(GameImage.LOADING, this.getGame().getDisplayWidth(), this.getGame().getDisplayHeight()), 0, 0))
    characterAppearance1.configureAppearance();
    characterAppearance2.configureAppearance();
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
  var dimension : Dimension = game.getDisplaySize()
  var matrixCharacter = new MatrixSelectedCharacter(GameValues.WIDTH_MATRIX ,GameValues.HEIGHT_MATRIX,this)
  var cursorFirstPlayer = new Cursor()
  var cursorSecondPlayer = new Cursor()
  var characterFirstSelected: SelectableCharacter = new SelectableCharacter(Ragna)
  var characterSecondSelected: SelectableCharacter = new SelectableCharacter(Litchi)
  this.matrixCharacter.loadSelectedCharacter(characterFirstSelected)
  this.matrixCharacter.loadSelectedCharacter(characterSecondSelected)
  
  var imageBigAllCharacter : Sprite = this.matrixCharacter.paintSelectedCharacter()
  var imageSelectedFirstCharacter = new GameComponent[GameScene, Animation](characterFirstSelected.character.selectedAnimation,-150,200)	
  var imageSelectedSecondCharacter = new GameComponent[GameScene, Animation](characterSecondSelected.character.selectedAnimation,700 , 200)	

  var backgrondImage1 = ScaleSpriteComponent.scale(sprite("00002.png"), game.getDisplayWidth(), game.getDisplayHeight())
  var backgrondImage2 = ScaleSpriteComponent.scale(sprite("00003.png"), game.getDisplayWidth(), game.getDisplayHeight())
  var backgrondImage3 = ScaleSpriteComponent.scale(sprite("00004.png"), game.getDisplayWidth(), game.getDisplayHeight())
  var backgrondAnimation = new Animation(1, 1, backgrondImage1, backgrondImage2, backgrondImage3);

  this.addComponent(new AnimationComponent(backgrondAnimation, 0, 0))
  this.addComponent(new SelectComponent(this, characterFirstSelected, GameValues.COORD_X_MATRIX ,GameValues.COORD_Y_MATRIX ,GameValues.WIDTH_SELECTED_CHARACTER, GameValues.HEIGHT_SELECTED_CHARACTER))
  this.addComponent(new SelectComponent(this, characterSecondSelected,GameValues.COORD_X_MATRIX + GameValues.WIDTH_SELECTED_CHARACTER + GameValues.PADDING_CHARACTER, GameValues.COORD_Y_MATRIX, GameValues.WIDTH_SELECTED_CHARACTER, GameValues.HEIGHT_SELECTED_CHARACTER))
  this.addComponent(imageSelectedFirstCharacter)
  //this.addComponent(imageSelectedSecondCharacter)
  var start = GameImage.BUTTON_START

  this.addComponent(new ButtonComponent(start, start, game.getDisplayWidth() - (start.getWidth() + 100), game.getDisplayHeight() - (start.getHeight() + 100), onStart))

  this.addComponent(new MouseComponent(GameImage.SWORD, 0, 0))
  this.addComponent(new MouseComponent(GameImage.SWORD, 200,200))

  override def selectItem(selectable: Selectable) {
    selectable match {
      case selectedCharacter: SelectableCharacter => {
        characterFirstSelected = selectedCharacter
        imageSelectedFirstCharacter.setAppearance(characterFirstSelected.character.selectedAnimation)

      }
    }

  }

  def onStart(delta: DeltaState) {
    game.selectArena(characterFirstSelected)
  }
}