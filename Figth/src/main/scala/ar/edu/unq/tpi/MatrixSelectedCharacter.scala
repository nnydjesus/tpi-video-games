package ar.edu.unq.tpi

import scala.collection.mutable.Map
import ar.edu.unq.tpi.resource.Matrix
import java.awt.image.BufferedImage
import com.uqbar.vainilla.appearances.Sprite
import ar.unq.tpi.components.SelectComponent
import scala.collection.mutable.Buffer

class MatrixSelectedCharacter(c: Int, f: Int, scene: SelectCharacterScene) extends Matrix[SelectableCharacter](c, f) {
  var pi = 0
  var pj = 0
  println("filas: " + f + " columnas " + c)
  
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
       this(i, j).image.renderAt(i * GameValues.WIDTH_SELECTED_CHARACTER, j * GameValues.HEIGHT_SELECTED_CHARACTER, graphics)
      })
    })
    new Sprite(imageBigCharacter)
  }
//  def createComponents() {
//    var mapComponents = Map[(Int, Int), SelectableCharacter]()
//    this.elems.keys.foreach(i => {
//      this.elems(i).keys.foreach(j => {
//        mapComponents((i * GameValues.WIDTH_SELECTED_CHARACTER + GameValues.COORD_X_MATRIX, j * GameValues.HEIGHT_SELECTED_CHARACTER + GameValues.COORD_Y_MATRIX)) = this(i, j)
//      })
//    })
//  }

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
