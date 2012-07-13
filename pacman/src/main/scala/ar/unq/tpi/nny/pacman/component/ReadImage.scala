package ar.unq.tpi.nny.pacman.component
import com.uqbar.vainilla.appearances.Animation

import ar.edu.unq.tpi.resource.TraitResources
import ar.unq.tpi.nny.pacman.util.GameConstants
import ar.unq.tpi.nny.pacman.util.GameImages

trait ReadImage extends TraitResources{

  var southAnimation:Animation = _
  var westAnimation:Animation = _
  var eastAnimation:Animation = _
  var northAnimation:Animation = _
  var vulnerableAnimation:Animation = _
  var eyeAnimation:Animation = _
  val sizeSprite = 32D
  val scale = GameConstants.TILE/sizeSprite
  
  def generateAnimations(){
    var image = sprite(name)
    
    westAnimation = new Animation(meantime, spriteS(sizeSprite, sizeSprite, 2, 1, image.getImage(), scale, scale))
    southAnimation = new Animation(meantime, spriteS(sizeSprite, sizeSprite, 2, 1, image.getImage(), scale, scale, 0,1))
    eastAnimation = new Animation(meantime, spriteS(sizeSprite, sizeSprite, 2, 1, image.getImage(), scale, scale, 0,2))
    northAnimation = new Animation(meantime, spriteS(sizeSprite, sizeSprite, 2, 1, image.getImage(), scale, scale,0, 3))
    vulnerableAnimation = new Animation(meantime, spriteS(sizeSprite, sizeSprite, 2, 1, GameImages.BLUE_GHOST.getImage(), scale, scale))
    eyeAnimation = new Animation(meantime, 0, GameImages.EYE_GHOST)
  }

  def meantime:Double = 0.2
  def name:String
}

