package ar.edu.unq.tpi

import scala.collection.mutable.Map
import ar.edu.unq.tpi.resource.Matrix
import java.awt.image.BufferedImage
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.components.SelectComponent
import scala.collection.mutable.Buffer
import ar.edu.unq.tpi.resource.TraitResources

class MatrixSelectedCharacter(c: Int, f: Int, scene: SelectCharacterScene) extends Matrix[SelectableCharacter](c, f) with TraitResources{
  var pi = 0
  var pj = 0
  
  def loadSelectedCharacter(characters: Buffer[CharacterAppearance]) {
    characters.foreach(c => {
      if(isOverflow(pi,pj)){
      this.nextCharacterSelected()  
      }
      this.put(pi, pj, new SelectableCharacter(c))
      this.nextCharacterSelected()
    })
  }

  def paintSelectedCharacter(): Sprite = {
    var imageBigCharacter: BufferedImage = new BufferedImage(GameValues.WIDTH_MATRIX * GameValues.WIDTH_SELECTED_CHARACTER, GameValues.HEIGHT_MATRIX * GameValues.HEIGHT_SELECTED_CHARACTER, BufferedImage.TYPE_INT_ARGB)
    var graphics: java.awt.Graphics2D = imageBigCharacter.createGraphics()
    this.elems.keys.foreach(i => {
      this.elems(i).keys.foreach(j => {
       sprite("backgroundMatrix.png").renderAt(i * GameValues.WIDTH_SELECTED_CHARACTER, j * GameValues.HEIGHT_SELECTED_CHARACTER, graphics)
       this(i, j).image.renderAt(i * GameValues.WIDTH_SELECTED_CHARACTER, j * GameValues.HEIGHT_SELECTED_CHARACTER, graphics)
      })
    })
    new Sprite(imageBigCharacter)
  }

  def nextCharacterSelected() {
    if (pi < width) {
      if (pj < height) {
        pj += 1
      } else {
        pi += 1
        pj = 0
      }
    } else {
      pi = 0
    }
  }

}
